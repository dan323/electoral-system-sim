package danieldlc.elections.utils;

import org.junit.Assert;
import org.junit.Test;

public class PairTest {

    private static final Pair<String,String> pair= new Pair<>("Key","Value");

    @Test
    public void pairTest(){
        Assert.assertEquals("Key",pair.getKey());
        Assert.assertEquals("Value",pair.getValue());
    }

    @Test
    public void comparePairTest(){
        Pair<String,String> pair2=new Pair<>("Key","Valua");
        Pair<String,String> pair3=new Pair<>("Key","Valuz");
        Assert.assertTrue(pair.compareTo(pair2)<0);
        Assert.assertTrue(pair.compareTo(pair3)>0);
    }

    @Test
    public void toStringPairTest(){
        Assert.assertEquals("Key -> Value",pair.toString());
    }
}
