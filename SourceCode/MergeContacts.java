import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * Given 1 million email list:
	list 1: a@a.com, b@b.com
	list 2: b@b.com, c@c.com
	list 3: e@e.com
	list 4: a@a.com
	...
	Combine lists with identical emails, and output tuples:
	(list 1, list 2, list 4) (a@a.com, b@b.com, c@c.com)
	(list 3) (e@e.com)
	union find  -  
 */
//    Contact group, 输入是这样的:数字是用户，字母是邮箱，有很多人有多个邮箱，找出相同的用户
//    1- {x,y,z}
//    2-{x}
//    3-{a,b}
//    4-{y, z}
//    5-{b}
//    6-{m}
//    7-{t,b}
//
//    这样输出就是 [1,2,4] [3,5,7], [6] 这三组
//    可以用UnionFind或者Connected Components的方法做
// For example, 1 - {x, y, z} 2 - {a, b}, 3 - {x} 4 - {x, a}
// first we map x -> 1, y -> 1, z -> 1 and 1 - > 1
// we meet 2, then map a -> 2, b -> 2, and 2 -> 2
// Then meet 3, find out that x -> 1, so 3 -> 1
// Then meet 4, find out that x -> 1, so 4 -> 1
// and find out that a -> 2, so  4's root 1's root should be 2,
// that is 1 -> 2
// in the end we have 1 -> 2, 2 -> 2, 3 -> 1, 4 -> 2
// So the gourp is [1, 2, 3, 4]
//    'Time complexity: O(nklgn) - n person and k emails in average,
//    findRoot, act like find node in a tree - O(lgn)
//    Space complexity: O(nk)'


public class MergeContacts {
	
	public List<List<Integer>> contactGroupUF(ArrayList<List<String>> contact) {
		HashMap<String, Integer> map = new HashMap<>();
		int[] id = new int[contact.size()];
		int count = contact.size();
		
		for (int i = 0; i < contact.size(); i++) {
			id[i] = i;
			for (String email : contact.get(i)) {
				if (!map.containsKey(email)) {
					map.put(email, i);
					continue;
				}
				int p = root(id, map.get(email));
				int q = root(id, i);
				if (p == q) continue;
				id[q] = p;
				count--;
			}
		}
		
		System.out.println("Group count: " + count);
		
		return null;
	}
	
	private int root(int[] id, int p) {
		while (p != id[p]) {
			id[p] = id[id[p]];
			p = id[p];
		}
		return p;
	}
	
	
	/*
	 * A cleaner solution, still union find,  same time complexity
	 */
    public List<List<Integer>> contactGroup(ArrayList<List<String>> contact) {
        HashMap<String, Integer> emailToPerson = new HashMap<>();//key is the emails, val is the peoson
        HashMap<Integer, Integer> id = new HashMap<>();
        for (int person = 0; person < contact.size(); person++) {
            id.put(person, person);
            int curRoot = findRoot(id, person);
            for (String email : contact.get(person)) {
                if (!emailToPerson.containsKey(email)) {//if curr email hasn't been mapped to a root(person) yet
                    emailToPerson.put(email, person);
                    continue;
                }
                int newRoot = emailToPerson.get(email);//get the root that this email mapped to
                newRoot = findRoot(id, newRoot);//get the oldest root that curr email's root mapped to
                if (newRoot != curRoot) {//if the oldest root is different from curr root(curr person)
                    id.put(curRoot, newRoot);//union curRoot to newRoot(which is the oldest root we added)
                    curRoot = newRoot;//remember to update curr list of emails' root
                }
            }
        }
        HashMap<Integer, List<Integer>> groups = new HashMap<>();//key is the root, val is the group of people
        List<List<Integer>> result = new ArrayList<>();
        for (int person : id.keySet()) {//put all people that have same root into a group list
            int root = findRoot(id, person);
            if (!groups.containsKey(root)) {
                groups.put(root, new ArrayList<Integer>());
            }
            groups.get(root).add(person);
        }
        
        for (int group : groups.keySet()) {
            result.add(groups.get(group));
        }
        
        for (List<Integer> res : result) {
        	System.out.println(Arrays.toString(res.toArray()));
        }
        return result;
    }
    
    private int findRoot(HashMap<Integer, Integer> personToRoot, int root) {
        while (personToRoot.get(root) != root) {
            root = personToRoot.get(root);
        }
        return root;
    }
	
	public static void main(String[] args) {
		MergeContacts clz = new MergeContacts();
		ArrayList<List<String>> contact = test2();
	
//		clz.merge(contact);
		clz.contactGroup(test1());
		clz.contactGroupUF(test1());
		
		clz.contactGroup(test2());
		clz.contactGroupUF(test2());
		
		clz.contactGroup(test3());
		clz.contactGroupUF(test3());
	}
	
	
	
	public static ArrayList<List<String>> test1() {
		ArrayList<List<String>> contact = new ArrayList<List<String>>();
		List<String> l1 = Arrays.asList("x", "y", "z");
		List<String> l2 = Arrays.asList("a", "b");
		List<String> l3 = Arrays.asList("x");
		List<String> l4 = Arrays.asList("x", "a");
		contact.add(l1);
		contact.add(l2);
		contact.add(l3);
		contact.add(l4);
		return contact;
	}
	
	public static ArrayList<List<String>> test2() {
		ArrayList<List<String>> contact = new ArrayList<List<String>>();
		List<String> l1 = Arrays.asList("a", "b");
		List<String> l2 = Arrays.asList("b", "c");
		List<String> l3 = Arrays.asList("e");
		List<String> l4 = Arrays.asList("a");
		contact.add(l1);
		contact.add(l2);
		contact.add(l3);
		contact.add(l4);
		return contact;
	}
	
	
	public static ArrayList<List<String>> test3() {
		ArrayList<List<String>> contact = new ArrayList<List<String>>();
		List<String> l1 = Arrays.asList("x", "y", "z");
		List<String> l2 = Arrays.asList("x");
		List<String> l3 = Arrays.asList("a", "b");
		List<String> l4 = Arrays.asList("y", "z");
		contact.add(l1);
		contact.add(l2);
		contact.add(l3);
		contact.add(l4);
		contact.add(Arrays.asList("b"));
		contact.add(Arrays.asList("m"));
		contact.add(Arrays.asList("t", "b"));
		return contact;
	}
}
