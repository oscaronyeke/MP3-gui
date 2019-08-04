package test.persistence;

import data.persistence.PreparedStatements;

/**
 * File: PurgeTables.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class PurgeTables {
    public static void main(String[] args) {
        PreparedStatements.truncateTables(true);
    }
}
