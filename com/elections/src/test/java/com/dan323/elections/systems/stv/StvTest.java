package com.dan323.elections.systems.stv;

import com.dan323.elections.ElectionMethodsTestReflectionUtils;
import com.dan323.elections.simulations.PartyListVotingSimulation;
import com.dan323.elections.systems.quo.Quota;
import com.dan323.elections.systems.quo.Quotas;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

/**
 * @author danco
 */
public class StvTest {

    private static final int NUMBER_OF_REPETITIONS = 10;
    private static List<Quota<List<String>, Double>> quotas;
    private static List<STVTransfer<String>> voteTransfers;
    private static List<STVChoice<String>> voteChoice;


    @BeforeAll
    public static void init() {
        voteTransfers = ElectionMethodsTestReflectionUtils.getTransferVotesMethods(List.of(VoteTransfers.class));
        voteChoice = ElectionMethodsTestReflectionUtils.getChoiceVotesMethods(List.of(VoteTransfers.class));
        quotas = ElectionMethodsTestReflectionUtils.getQuotaMethods(List.of(Quotas.class));
    }

    @Test
    public void methodSTVAllTest() {
        for (STVChoice<String> choiceMethod : voteChoice) {
            for (STVTransfer<String> transferMethod : voteTransfers) {
                for (int j = 0; j < NUMBER_OF_REPETITIONS; j++) {
                    oneRandomTest(choiceMethod, transferMethod);
                }
            }
        }
    }

    private void oneRandomTest(STVChoice<String> choiceMethod, STVTransfer<String> transferMethod) {
        VoteTransfers.STVMethod(choiceMethod, transferMethod, new HashMap<>(), 3);
    }

}
