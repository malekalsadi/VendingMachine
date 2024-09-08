package com.progressoft.samples;

import java.util.*;

public class Money {

    public static final Money Zero = new Money();
    public static final Money OnePiaster = new Money();
    public static final Money FivePiasters = new Money();
    public static final Money TenPiasters = new Money();
    public static final Money TwentyFivePiasters = new Money();
    public static final Money FiftyPiasters = new Money();
    public static final Money OneDinar =  new Money();
    public static final Money FiveDinars = new Money();
    public static final Money TenDinars = new Money();
    public static final Money TwentyDinars = new Money();
    public static final Money FiftyDinars = new Money();

    static {
        Zero.AvailableMoney.add(Zero);
        OnePiaster.AvailableMoney.add(OnePiaster);
        FivePiasters.AvailableMoney.add(FivePiasters);
        TenPiasters.AvailableMoney.add(TenPiasters);
        TwentyFivePiasters.AvailableMoney.add(TwentyFivePiasters);
        FiftyPiasters.AvailableMoney.add(FiftyPiasters);
        OneDinar.AvailableMoney.add(OneDinar);
        FiveDinars.AvailableMoney.add(FiveDinars);
        TenDinars.AvailableMoney.add(TenDinars);
        TwentyDinars.AvailableMoney.add(TwentyDinars);
        FiftyDinars.AvailableMoney.add(FiftyDinars);
    }

    public List<Money> AvailableMoney = new ArrayList<>();

    private Money() {}

    public double amount() {
        double amount = 0.0;
        for (Money money: AvailableMoney) {
            if (money == Zero)
                amount += 0.0;
            else if (money == OnePiaster)
                amount += 0.01;
            else if (money == FivePiasters)
                amount += 0.05;
            else if (money == TenPiasters)
                amount += 0.10;
            else if (money == TwentyFivePiasters)
                amount += 0.25;
            else if (money == FiftyPiasters)
                amount += 0.5;
            else if (money == OneDinar)
                amount += 1.0;
            else if (money == FiveDinars)
                amount += 5.0;
            else if (money == TenDinars)
                amount += 10.0;
            else if (money == TwentyDinars)
                amount += 20.0;
            else if (money == FiftyDinars)
                amount += 50.0;
            else
                amount += money.amount();
        }
        return amount;
    }

    public Money times(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count cannot be negative");
        }

        Money money = new Money();
        for (int i = 0; i < count; i++) {
            money.AvailableMoney.addAll(this.AvailableMoney);
        }
        return money;
    }

    public static Money sum(Money... items) {
        Money money = new Money();
        for (Money item : items) {
            money.AvailableMoney.addAll(item.AvailableMoney);
        }
        return money;
    }

    public Money plus(Money other) {
        Money money = new Money();
        money.AvailableMoney = new ArrayList<>(this.AvailableMoney);
        money.AvailableMoney.addAll(other.AvailableMoney);
        return money;
    }

    private Map<Integer, Boolean> knapSack(double target, Map<Integer,Boolean> returned, int idx, List<Money> availableMoney, Map<Integer,Boolean> ans) {
        if(target == 0){
            for (Map.Entry<Integer, Boolean> entry : returned.entrySet()) {
                Integer key = entry.getKey();
                Boolean value = entry.getValue();
                ans.put(key, value);
            }
        }
        if(idx >= availableMoney.size()) return ans;

        returned.put(idx, true);
        knapSack(target-availableMoney.get(idx).amount(), returned, idx+1, availableMoney, ans);

        returned.put(idx, false);
        knapSack(target, returned, idx+1, availableMoney, ans);

        return ans;
    }
    public Money minus(Money other) {
        double result = amount() - other.amount();
        if (result < 0) {
            throw new IllegalArgumentException("Resulting money cannot be negative");
        }
        if(result == 0)
            return Zero;

        Map<Integer, Boolean> returnedAns = new HashMap<>();
        knapSack(other.amount(), new HashMap<>(), 0, this.AvailableMoney, returnedAns);

        if(returnedAns.isEmpty())
            throw new IllegalArgumentException("Available bills or coins are not enough for the change");


        Money money = new Money();
        for (int i=0; i < this.AvailableMoney.size(); i++) {
            if(!returnedAns.get(i))
                money.AvailableMoney.add(this.AvailableMoney.get(i));
        }
        return money;
    }


    @Override
    public String toString() {
        return String.format("%.2f", amount());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Money money = (Money) obj;
        double epsilon = 0.01d;
        return Math.abs(this.amount()-money.amount()) < epsilon;
    }





}

