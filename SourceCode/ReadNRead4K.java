
public class ReadNRead4K {
	
	/**
	 * 157. Read N Characters Given Read4
	 * 如果要读的很长，读到N 为止
	 * 如果要读的很短，那就就看什么时候read4 retrun < 4 了, 表明已经没有超过四个可以读了
	 */
	public int read(char[] buf, int n) {
		boolean eof = false; // end of file flag
		int offset = 0; // total bytes have read
		char[] tmp = new char[4]; // temp buffer

		while (!eof && offset < n) {
			int count = read4(tmp);
			// get the actual count
			count = Math.min(count, n - offset);

			// copy from temp buffer to buf
			for (int i = 0; i < count; i++)
				buf[offset++] = tmp[i];
			
			// check if it's the end of the file
			eof = count < 4;
		}
		return offset;
		
	}
	
	
	/**
	 * 158. Read N Characters Given Read4 II - Call multiple times
	 * 这个和上面一题的区别在于，如果只读一次，超过N长度的部分可以抛弃， 比如
	 *  abcdefgh,  n = 6, 那么我们只需要读 abcdef 就可以了，后面的不用管。
	 *  
	 * 但是如果使用多次的话，假如还是 abcdefgh 第一次 n = 6, 读了abcdef
	 * 第二次 n = 1, 如果后面都抛弃了，那么就读不到东西了，所以需要一个buffStr 来记录已经被读的字符
	 * 同时还要一个 hasReadLen 记录一共被buffer读了多少个。
	 * 
	 * abcdefgh
	 * 第一次 n = 6,  buffstr = abcdefg,  hasReadLen = 6, read4被调用两次
	 * 第二次 n = 1,  read4调用了但是没有东西返回，因为都读完了, 从buffStr里面hasReadLen的位置继续往后读
	 */
	String buffStr = "";
	int hasReadLen = 0;
	public int readMultiple(char[] buf, int n) {
		int offset = 0;
		char[] buff4 = new char[4];
		while (offset < n) {
			int len = read4(buff4);
			for (int i = 0; i < len; i++) {
				buffStr += buff4[i];
			}
			
			while (offset < n && offset + hasReadLen < buffStr.length()) {
				buf[offset] = buffStr.charAt(offset + hasReadLen);
				offset++;
			}
			
			if (len == 0) break;
		}
		hasReadLen += offset;
		return offset;
	}
	
	StringBuilder cache_sb = new StringBuilder();
    int hasRead = 0;
	public int readMultiple2(char[] buf, int n) {
		int offset = 0;
		char[] buff4 = new char[4]; boolean eof = false;
		while (offset < n && !eof) {
			while (hasReadLen < cache_sb.length() && offset < n) {
				buf[offset++] = cache_sb.charAt(hasReadLen++);
			}
			if (hasReadLen == cache_sb.length()) {
				hasReadLen = 0; cache_sb = new StringBuilder();
			}
			if (offset == n) break;
			
			int len = read4(buff4);
			int i = 0;
			while (offset < n && i < len) {
				buf[offset++] = buff4[i++];
			}
			
			while (i < len) {
				cache_sb.append(buff4[i++]);
			}
			
			if (len < 4) {
				eof = true;
			}
		}
		return offset;
	}
	
	/**
	 * Followup: optimize when N is larger than 4K。 文件和读取size都很大怎么办。
	 * 有个假设： 如果可以调用的API 改变成 int read4(char buf, int start). 
	 * 默认 buf 足够长， start 为上次读完之后的下一个空index. read4k(buf, start) 可以用readN的buf 
	 * start是读的起始位置 
	 * 
	 * 解法大概是假设n是4k*i + j大小，前面i下就直接读到buf里，或者读没了直接返回。最后一下读到cache里？
	 * 下次读的时候先把cache读出来再继续
	 *                   
	 * abcd efgh ijkl 
	 * 			  n = 6, 第一次四个直接调用read4(buf, 0) 读到buf里面，offset = 4， len = 4, buf = abcd
	 * 					 然后由于 n - offset = 2, 所以读到 buff4 里面，buff4 = efgh, len = 4 但是我们只
	 * 					 需要两位了，所以 i = 2 的时候循环跳出， 此时 buf = abcdef， i = 2, 所以把剩下的两位
	 * 					 放到缓存里面, cache = gh
	 * 			  n = 4, 第二次要读4个，首先检查缓存，发现缓存有两个。所以先把缓存的读出来,此时readlen = 2,offset = 2
	 * 					 缓存都读完了，就清空了。 n - offset < 4， 所以还是要读到 buff4里面。然后buff4前两位给buf
	 * 					 后两位给cache.   buff = bhij, readLen = 0, cache=kl
	 * 			  n = 2, 第三次要读2个，考虑到缓存还有两位，所以直接从cache里面读 readLen =2, 清空重置缓存。offset = 2
	 * 					 offset == n,不需要继续读了。
	 * 
	 * abcd efgh ijkl
	 *            n = 8, 第一次读，直接读到buf里面，offset = len = 4, 第二次n - offset >= 4, 所以还是读到buf
	 *            		 里面，read4(buf, 4) , 然后 offest = 8 = n, 就不再读了。		
	 */
	String cache = "";
	int readLen = 0;
	public int readLarge(char[] buf, int n) {
		int offset = 0;
		while (offset < n) {
			//check if cache is null
			while (hasReadLen < cache_sb.length() && offset < n) {
				buf[offset++] = cache_sb.charAt(hasReadLen++);
			}
			if (hasReadLen == cache_sb.length()) {
				hasReadLen = 0; cache_sb = new StringBuilder();
			}
			
			if (offset == n) break;  //缓存够了，不需要继续了
			int len = 0;
			if (n - offset >= 4) {
				len = read4(buf, offset);
				offset += len;
			} else {
				char[] buff4 = new char[4];
				len = read4(buff4, 0);
				int i = 0;
				for (;i < len && offset < n; i++) {
					buf[offset++] = buff4[i];
				}
				while (i < len) {
					cache += buff4[i++];
				}
			}
			
			if (len == 0) break;
		}
		return offset;
	}
	
	
	/**
	 * Read4K - Given a function which reads from a file or network stream up to 4k at a time, give a function which 
	 * which can satisfy requests for arbitrary amounts of data. Due to network latency, if the read4K method return 
	 * a value < 4k, it does not necessarily mean that we reach the End of File (EOF), in this case, you should continue 
	 * to read the file until you reach toRead or EOF.
	 * 
	 * EOF: byte[offset-1]  == 0;
	 */
	public int readTillEOF(char[] buf, int n) {
		int offset = 0;
		boolean eof = false;
		
		while (offset < n && !eof) {
			char[] tmp = new char[4];
			int len = read4(tmp);
			//read(byte[], int, int) returns -1. usually
			if (len == -1) {
				eof = true;
				continue;
			}
			
			for (int i = 0; i < len && offset < n; i++) {
				buf[offset++] = tmp[i];
			}
			
//			if (offset > 0 && buf[offset-1] == -1) {
//				eof = true;
//			}
			
			
		}
		
		return offset;
	}

	public int read4(char[] buffer) {
		return 4;
	}
	
	public int read4(char[] buffer, int start) {
		return 4;
	}
}
