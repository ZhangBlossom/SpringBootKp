package blossom.project.springbootkp;


import java.util.BitSet;

/**
 * @author: 张锦标
 * @date: 2023/7/15 10:43
 * BloomFilter类
 */

public class BloomFilter {
    public   final Integer BIT_SIZE = 2 << 32;

    public   final Integer[] HASH_SEEDS = new Integer[]{3, 7, 13, 20, 56, 88, 134};

    private   BitSet bitSet = new BitSet(BIT_SIZE);

    private   HashCodeGenerator[] hashCodeGenerators = new HashCodeGenerator[HASH_SEEDS.length];

    public BloomFilter() {
        for (int i = 0; i < hashCodeGenerators.length; i++) {
            hashCodeGenerators[i] = new HashCodeGenerator(HASH_SEEDS.length,HASH_SEEDS[i]);
        }
    }

    public   void add(String value) {
        for (HashCodeGenerator hashCodeGenerator : hashCodeGenerators) {
            bitSet.set(hashCodeGenerator.hash(value),true);
        }
    }

    public   boolean contain(String value){
        boolean res = true;
        for (HashCodeGenerator hashCodeGenerator : hashCodeGenerators) {
            boolean b = bitSet.get(hashCodeGenerator.hash(value));
            if (!b){
                return false;
            }
        }
        return res;
    }

    public static   void main(String[] args) {
        BloomFilter bf = new BloomFilter();
        for (int i = 0; i < 10000; i++) {
            bf.add(String.valueOf(i));
        }
        String id = "1314";
        bf.add(id);

        System.out.println(bf.contain(id));   // true
        System.out.println("" + bf.contain("520123000"));  //false
    }
}
class HashCodeGenerator{
    private int size;
    private int seed;

    public HashCodeGenerator(int size, int seed) {
        this.size = size;
        this.seed = seed;
    }

    public int hash(String value) {
        int result = 0;
        int len = value.length();
        for (int i = 0; i < len; i++) {
            result = seed * result + value.charAt(i);
        }
        int r = (size - 1) & result;
        return (size - 1) & result;
    }

}