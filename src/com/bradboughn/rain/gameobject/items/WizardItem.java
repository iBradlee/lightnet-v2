
package com.bradboughn.rain.gameobject.items;

public enum WizardItem {
    
    WIZARD (1, "Plasma Blast", 6.0, 15.0, 200.0, 20.0, 0);
    
    private int ID;
    private String name;
    
    private double speed;
    private double fireRate;
    private double range;
    private double damage;
    private double dropRate;

    
    
    private WizardItem(int ID, String name, double speed, double fireRate, double range, double damage,
            double dropRate)
    {
        this.ID = ID;
        this.name = name;
        this.speed = speed;
        this.fireRate = fireRate;
        this.range = range;
        this.damage = damage;
        this.dropRate = dropRate;
    }
    
}
