package mainapp;

import bet.BetApp;
import event.EventApp;
import market.MarketApp;
import outcome.OutcomeApp;
import utilities.DependencyInjector;
import utilities.Result;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MainApp {
    private EventApp eventApp = DependencyInjector.EventApp();
    private MarketApp marketApp = DependencyInjector.MarketApp();
    private OutcomeApp outcomeApp = DependencyInjector.OutcomeApp();
    private BetApp betApp = DependencyInjector.BetApp();

    public static void main(String[] args) {
        new MainApp();
    }

    private  MainApp() {
        System.out.println("BETTING ENGINE v1");
        createEvents();
        createMarkets();
        createOutcomes();
        createBets();
        printProfits();
    }

    private void createEvents() {
        System.out.println("--------------------------------------------");
        System.out.println("\t\t\tCreating events");
        System.out.println("--------------------------------------------");
        Result result = eventApp.createEvent("Rugby World Cup 2020", LocalDateTime.now(), LocalDateTime.now(), "EVENT_9c1374f6-d9de-4526-8034-42e9b321980e");
        System.out.println(result.getResultStatus() + "...Rugby World Cup 2020 ");

    }

    private void createMarkets() {
        System.out.println("--------------------------------------------");
        System.out.println("\t\t\tCreating markets");
        System.out.println("--------------------------------------------");
        Result resultZAvsNZ = marketApp.createMarket("EVENT_9c1374f6-d9de-4526-8034-42e9b321980e", "South Africa vs New Zealand Final", "MARKET_ec264cd4-1eff-4810-8937-e338787a447c");
        Result resultZAvsENG = marketApp.createMarket("EVENT_9c1374f6-d9de-4526-8034-42e9b321980e", "South Africa vs England Final", "MARKET_e3a597cf-61e1-4725-b498-515f1f94c52f");
        System.out.println(resultZAvsNZ.getResultStatus() + "...South Africa vs New Zealand Semi Final");
        System.out.println(resultZAvsENG.getResultStatus() + "...South Africa vs England Final");
    }

    private void createOutcomes() {
        System.out.println("--------------------------------------------");
        System.out.println("\t\t\tCreating outcomes");
        System.out.println("--------------------------------------------");
        ArrayList<Result> outcomeResults = new ArrayList<Result>();
        outcomeResults.add(outcomeApp.createOutcome("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "South Africa wins", 0.3, "OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5"));
        outcomeResults.add(outcomeApp.createOutcome("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "New Zealand wins", 0.7, "OUTCOME_f8b48f37-7752-43e4-953e-5abcb5583b66"));
        outcomeResults.add(outcomeApp.createOutcome("MARKET_e3a597cf-61e1-4725-b498-515f1f94c52f", "South Africa wins", 0.2, "OUTCOME_e335c45a-9ab0-4108-aeab-8d6b6ec3bf91"));
        outcomeResults.add(outcomeApp.createOutcome("MARKET_e3a597cf-61e1-4725-b498-515f1f94c52f", "England wins", 0.8, "OUTCOME_6ff32113-bcdf-4c7d-a5f7-953a076245ad"));
        System.out.println(outcomeResults.get(0).getResultStatus() + "...South Africa wins (South Africa vs New Zealand Semi Final)");
        System.out.println(outcomeResults.get(1).getResultStatus() + "...New Zealand wins (South Africa vs New Zealand Semi Final)");
        System.out.println(outcomeResults.get(2).getResultStatus() + "...South Africa wins (South Africa vs England Final))");
        System.out.println(outcomeResults.get(3).getResultStatus() + "...England wins (South Africa vs England Final)");
    }

    private void createBets() {
        System.out.println("--------------------------------------------");
        System.out.println("\t\t\tCreating Bets");
        System.out.println("--------------------------------------------");
        System.out.println(betApp.placeBet("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5", 5.5, "BET_34caceed-5f75-4c03-b295-508e7c64f412").getResultStatus() + "...R5.5 placed against South Africa wins (South Africa vs New Zealand Semi Final)");
        System.out.println(betApp.placeBet("OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5", 4.5, "BET_1d487d68-4a1d-49fd-b1cd-1ff818fb0269").getResultStatus() + "...R4.5 placed against South Africa wins (South Africa vs New Zealand Semi Final)");

        System.out.println(betApp.placeBet("OUTCOME_f8b48f37-7752-43e4-953e-5abcb5583b66", 10, "BET_abb748d5-81df-4e60-a4aa-4938b7887538").getResultStatus() + "...R10 New Zealand wins (South Africa vs New Zealand Semi Final)");
        System.out.println(betApp.placeBet("OUTCOME_f8b48f37-7752-43e4-953e-5abcb5583b66", 11, "BET_f89e62ab-1129-4b7c-a690-5c74c1dd30cc").getResultStatus() + "...R11 placed New Zealand wins (South Africa vs New Zealand Semi Final)");

        System.out.println(betApp.placeBet("OUTCOME_e335c45a-9ab0-4108-aeab-8d6b6ec3bf91", 12, "BET_8824e720-e3a1-4009-a9d9-2ccf65c631cd").getResultStatus() + "...R12 placed against South Africa wins (South Africa vs England Final)");
        System.out.println(betApp.placeBet("OUTCOME_e335c45a-9ab0-4108-aeab-8d6b6ec3bf91", 12, "BET_bb4cf039-59a1-4d48-9908-9c6178489ec4").getResultStatus() + "...R12 placed against South Africa wins (South Africa vs England Final)");

        System.out.println(betApp.placeBet("OUTCOME_6ff32113-bcdf-4c7d-a5f7-953a076245ad", 14, "BET_18513d4c-42e9-4b19-a1fc-b74e710638bf").getResultStatus() + "...R14 placed against England wins (South Africa vs England Final)");
        System.out.println(betApp.placeBet("OUTCOME_6ff32113-bcdf-4c7d-a5f7-953a076245ad", 56, "BET_b2f18e89-2cdc-40d2-949e-37f65d2bd699").getResultStatus() + "...R56 placed against England wins (South Africa vs England Final)");

    }

    private void printProfits() {
        System.out.println("--------------------------------------------");
        System.out.println("\t\t\tProfit");
        System.out.println("--------------------------------------------");
        System.out.println("South Africa vs New Zealand Semi Final");
        System.out.println("South Africa wins : R" + (double)marketApp.calculateProfits("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "OUTCOME_a067ef9d-e7f9-4168-bad3-c6314a33edf5").getResultObject());
        System.out.println("New Zealand wins : R" + (double)marketApp.calculateProfits("MARKET_ec264cd4-1eff-4810-8937-e338787a447c", "OUTCOME_f8b48f37-7752-43e4-953e-5abcb5583b66").getResultObject());
        System.out.println("South Africa vs England Final");
        System.out.println("South Africa wins : R" + (double)marketApp.calculateProfits("MARKET_e3a597cf-61e1-4725-b498-515f1f94c52f", "OUTCOME_e335c45a-9ab0-4108-aeab-8d6b6ec3bf91").getResultObject());
        System.out.println("England wins : R" + (double)marketApp.calculateProfits("MARKET_e3a597cf-61e1-4725-b498-515f1f94c52f", "OUTCOME_6ff32113-bcdf-4c7d-a5f7-953a076245ad").getResultObject());
    }
}
