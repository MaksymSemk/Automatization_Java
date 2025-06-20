package org.example;

import java.util.Scanner;

public class AppMain {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        while (true){
            System.out.println("=".repeat(30));

            System.out.println("To exit program type \"exit\"");

            System.out.println("Enter material of tree:");
            String treeName= sc.nextLine();
            if(treeName.equals("exit")){
                return;
            }

            System.out.println("Enter weight of tree:");
            int treeWeight= Integer.parseInt(sc.nextLine());

            Tree t= new Tree(treeName, treeWeight);

            System.out.println("Validating tree...");
            if(TreeValidator.validate(t)){
                System.out.println("Its a valid tree");
                System.out.println("Refinning...");
                TreeHelper.refine(t);
            }
            else {
                System.out.println("Tree is not valid");
            }

        }
    }


}
