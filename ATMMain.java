import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            JOptionPane.showMessageDialog(null, "Insufficient funds. Withdrawal failed.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        balance -= amount;
        return true;
    }
}

class ATMGUI extends JFrame {
    private ATM atm;

    public ATMGUI(ATM atm) {
        this.atm = atm;
        setTitle("ATM Machine");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
    }

    private void createUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));

        JButton checkBalanceButton = new JButton("Check Balance");
        JButton withdrawButton = new JButton("Withdraw Money");
        JButton depositButton = new JButton("Deposit Money");
        JButton exitButton = new JButton("Exit");

        checkBalanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atm.checkBalance();
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String amountString = JOptionPane.showInputDialog("Enter withdrawal amount:");
                try {
                    double amount = Double.parseDouble(amountString);
                    atm.withdrawMoney(amount);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String amountString = JOptionPane.showInputDialog("Enter deposit amount:");
                try {
                    double amount = Double.parseDouble(amountString);
                    atm.depositMoney(amount);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(checkBalanceButton);
        panel.add(withdrawButton);
        panel.add(depositButton);
        panel.add(exitButton);

        getContentPane().add(panel);
    }
}

class ATM {
    private BankAccount userAccount;

    public ATM(BankAccount account) {
        this.userAccount = account;
    }

    public void checkBalance() {
        double balance = userAccount.getBalance();
        JOptionPane.showMessageDialog(null, "Current Balance: $" + balance, "Balance", JOptionPane.INFORMATION_MESSAGE);
    }

    public void withdrawMoney(double amount) {
        if (amount <= 0) {
            JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a positive value.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (userAccount.withdraw(amount)) {
            JOptionPane.showMessageDialog(null, "Withdrawal successful. Remaining balance: $" + userAccount.getBalance(), "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void depositMoney(double amount) {
        if (amount <= 0) {
            JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a positive value.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        userAccount.deposit(amount);
        JOptionPane.showMessageDialog(null, "Deposit successful. Updated balance: $" + userAccount.getBalance(), "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}

public class ATMMain {
    public static void main(String[] args) {
        BankAccount userAccount = new BankAccount(1000.0);
        ATM atm = new ATM(userAccount);

        SwingUtilities.invokeLater(() -> {
            ATMGUI atmGUI = new ATMGUI(atm);
            atmGUI.setVisible(true);
        });
    }
}
