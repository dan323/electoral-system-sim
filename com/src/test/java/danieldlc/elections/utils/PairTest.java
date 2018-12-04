package danieldlc.elections.utils;

import org.junit.Assert;
import org.junit.Test;

public class PairTest {

    private static final Pair<String,String> pair= new Pair<>("Key","Value");

    @Test
    public void pairTest(){
        Assert.assertEquals("Key",pair.getKey());
        Assert.assertEquals("Value",pair.getValue());
        pair.setValue("Value2");
        Assert.assertEquals("Value2",pair.getValue());
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

    @Test
    public void equalsPairTest(){
        Assert.assertEquals(pair, new Pair<>(pair.getKey(),pair.getValue()));
        Assert.assertNotEquals(pair, new Pair<>("A","B"));
        Assert.assertNotEquals(pair,"Testing");
        Assert.assertNotEquals(pair,new Pair<>(pair.getKey(),"B"));
        Assert.assertNotEquals(pair,new Pair<>("A",pair.getValue()));
    }

    @Test
    public void hashCodePairTest(){
        Pair<String,String>  pair2=new Pair<>(pair.getKey(),pair.getValue());
        Assert.assertEquals(pair2.hashCode(),pair.hashCode());
    }
}
