package com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CalcPi
{
    // Checkt ob Dart im Kreis gelandet ist
    public static boolean InsideCircle(double X, double Y)
    {
        double LandingPoint = Math.sqrt(Math.pow(X,2) + Math.pow(Y,2));

        if (LandingPoint < 1)
        {
            return true;
        }
        return false;
    };


    double calcPi(int threadcount)
    {
        long startTime = System.nanoTime();

        //Erzeuge Executor Thread Pool
        ExecutorService executor = Executors.newFixedThreadPool(threadcount);
        double dartsGesamt = Math.pow(10,8);
        int dartPakete = 10; // Dartsgesamt sollte ein vielfaches von Dartpakete sein
        double dartPerRun = dartsGesamt/dartPakete;

        //Task
        Callable<Integer> callableTask = () ->
        {
            int TrefferCount = 0;

            for( int j = 0; j < dartPerRun; j++)
            {
                double Dart_X = ThreadLocalRandom.current().nextDouble(-1, 1);
                double Dart_Y = ThreadLocalRandom.current().nextDouble(-1, 1);

                if (InsideCircle(Dart_X, Dart_Y))
                {
                    TrefferCount++;
                }
            }
            return TrefferCount;
        };

        List<Future<Integer>> future = new ArrayList<>();

        // Tasks werden in die Execution Pool gereiht
        for( int i = 0; i < dartPakete; i++)
        {
            future.add(executor.submit(callableTask));
        }

        double summe = 0;

        // Warte auf die Ergebnisse
        for( int i = 0; i < dartPakete; i++)
        {
            int ergebnis;
            try
            {
                ergebnis = future.get(i).get();

                summe = summe + ergebnis;
                System.out.println(("Ergebnis Task " + i + " ist " + ergebnis));
            }
            catch (InterruptedException | ExecutionException e)
            {
                e.printStackTrace();
            }
        }
        executor.shutdown();

        //Berechnung von PI
        double pi = ((double)summe/(double)dartsGesamt)*4;

        System.out.println("pi ist " + pi);

        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;

        System.out.println("CalcPI time elapsed: " + timeElapsed);

        return pi;

    }
}
