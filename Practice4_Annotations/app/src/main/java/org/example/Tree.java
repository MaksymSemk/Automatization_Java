package org.example;

@GenerateRefineryHelper
public class Tree {

    @ValidateTree
    String name;

    @ValidateTree(
            minValue = 1,
            maxValue = 1000
    )
    int weight;

    public Tree(String name, int weight) {
        this.name = name;
        this.weight=weight;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }
}
