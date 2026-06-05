import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;
import javax.swing.*;
import java.util.List;


public class GUI extends JFrame{
    private JPanel mainPanel;
    private Image logo = new ImageIcon(getClass().getResource("logo.jpg")).getImage();
    private Insertion insert;
    private Modification modify;
    private Query query;
    
    public GUI(){
        setTitle("Stock System");
        setSize(1200, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setIconImage(logo);
        setFocusableWindowState(false);
        insert = new Insertion();
        modify = new Modification();
        query = new Query();

        // establishing the main panel
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints layout = new GridBagConstraints();
        layout.fill = GridBagConstraints.HORIZONTAL;

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.white);
        ImageIcon logo = new ImageIcon("logo.jpg");
        JLabel logoLabel = new JLabel(logo);
        // logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel title = new JLabel("Stock System");
        title.setForeground(new Color(9, 59, 90));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        titlePanel.add(title, BorderLayout.NORTH);
        titlePanel.add(logoLabel, BorderLayout.CENTER);

        // insertion panel
        JPanel insertionPanel = new JPanel(new BorderLayout());
        JLabel insertionTitle =  makeSectionTitle("Insertion Section");

        insertionPanel.add(insertionTitle, BorderLayout.NORTH);

        JPanel insertionBodyPanel = new JPanel(new GridBagLayout());
        insertionBodyPanel.setBackground(new Color(152, 193, 217));

        JButton isnertInvestor = styleButton("Insert a New Investor");
        JButton insertCompany = styleButton("Insert a New Company");
        JButton insertStock = styleButton("Insert a New Stock");


        layout.insets = new Insets(10, 10, 10, 10);
        layout.gridx = 0;
        layout.gridy = 0;
        layout.gridwidth = 1;
        insertionBodyPanel.add(isnertInvestor, layout);

        layout.gridx = 1;
        insertionBodyPanel.add(insertCompany, layout);

        layout.gridx = 2;
        insertionBodyPanel.add(insertStock, layout);

        isnertInvestor.addActionListener(e -> {
            JDialog dialog = new JDialog(
                (Frame) SwingUtilities.getWindowAncestor(insertionPanel),
                "Insert Investor",
                true
            );

            dialog.setContentPane(insertInvestorPanel());

            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });

        insertCompany.addActionListener(e -> {
            JDialog dialog = new JDialog(
                (Frame) SwingUtilities.getWindowAncestor(insertionPanel),
                "Insert Investor",
                true
            );

            dialog.setContentPane(insertCompanyPanel());

            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });

        insertStock.addActionListener(e -> {
            JDialog dialog = new JDialog(
                (Frame) SwingUtilities.getWindowAncestor(insertionPanel),
                "Insert Investor",
                true
            );

            dialog.setContentPane(insertStockPanel());

            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });


        insertionPanel.add(insertionBodyPanel, BorderLayout.CENTER);


        // modification panel
        JPanel modificationPanel = new JPanel(new BorderLayout());
        modificationPanel.setBackground(new Color(152, 193, 217));
        JLabel modificationTitle =  makeSectionTitle("Modification Section");

        
        modificationPanel.add(modificationTitle, BorderLayout.NORTH);


        JPanel modificationBodyPanel = new JPanel(new GridBagLayout());
        modificationBodyPanel.setBackground(new Color(152, 193, 217));

        JButton updateInvestor = styleButton("Update Investor");
        JButton updateCompany = styleButton("Update Company");
        JButton updateStock = styleButton("Update Stock");

        layout.insets = new Insets(10, 10, 10, 10);
        layout.gridx = 0;
        layout.gridy = 0;
        layout.gridwidth = 1;
        modificationBodyPanel.add(updateInvestor, layout);

        layout.gridx = 1;
        modificationBodyPanel.add(updateCompany, layout);

        layout.gridx = 2;
        modificationBodyPanel.add(updateStock, layout);

        updateInvestor.addActionListener(e -> {
            JDialog dialog = new JDialog(
                (Frame) SwingUtilities.getWindowAncestor(modificationPanel),
                "Update Investor",
                true
            );
            dialog.setContentPane(updateInvestorPanel());
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });

        updateCompany.addActionListener(e -> {
            JDialog dialog = new JDialog(
                (Frame) SwingUtilities.getWindowAncestor(modificationPanel),
                "Update Company",
                true
            );
            dialog.setContentPane(updateCompanyPanel());
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });

        updateStock.addActionListener(e -> {
            JDialog dialog = new JDialog(
                (Frame) SwingUtilities.getWindowAncestor(modificationPanel),
                "Update Stock",
                true
            );
            dialog.setContentPane(updateStockPanel());
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });

        modificationPanel.add(modificationBodyPanel, BorderLayout.CENTER);

        // query panel
        JPanel queryPanel = new JPanel(new BorderLayout());
        queryPanel.setBackground(new Color(152, 193, 217));
        JLabel querytitle =  makeSectionTitle("Query Section");

        
        queryPanel.add(querytitle, BorderLayout.NORTH);
    
        JPanel queryBodyPanel = new JPanel(new GridBagLayout());
        queryBodyPanel.setBackground(new Color(152, 193, 217));

        JButton queryOneButton = styleButton("Transactions by Investor");
        JButton queryTwoButton = styleButton("Top Stocks by Volume");

        layout.insets = new Insets(10, 10, 10, 10);
        layout.gridx = 0;
        layout.gridy = 0;
        layout.gridwidth = 1;
        queryBodyPanel.add(queryOneButton, layout);

        layout.gridx = 1;
        queryBodyPanel.add(queryTwoButton, layout);

        queryOneButton.addActionListener(e -> {
            JDialog dialog = new JDialog(
                (Frame) SwingUtilities.getWindowAncestor(queryPanel),
                "Transactions by Investor",
                true
            );

            dialog.setContentPane(queryTransactionsByInvestorPanel());
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });

        queryTwoButton.addActionListener(e -> {
            JDialog dialog = new JDialog(
                (Frame) SwingUtilities.getWindowAncestor(queryPanel),
                "Top Stocks by Volume",
                true
            );

            dialog.setContentPane(queryTopStocksPanel());
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });

        queryPanel.add(queryBodyPanel, BorderLayout.CENTER);

        layout.insets = new Insets(0, 0, 0, 0);
        layout.gridx = 0;
        layout.fill = GridBagConstraints.BOTH;
        layout.weightx = 1.0;
        layout.gridy = 0;
        layout.weighty = 0.4;
        mainPanel.add(titlePanel, layout);
        layout.gridy = 1;
        layout.weighty = 1.0;
        mainPanel.add(insertionPanel, layout);
        layout.gridy = 2;
        layout.weighty = 1.0;
        mainPanel.add(modificationPanel, layout);
        layout.gridy = 3;
        layout.weighty = 2.0;
        mainPanel.add(queryPanel, layout);
        add(mainPanel);
        setVisible(true);
    }

    /**
     * 
     * @return a penl to inert investor
     */
    private JPanel insertInvestorPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(152, 193, 217));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        GridBagConstraints layout = new GridBagConstraints();
        layout.insets = new Insets(8, 8, 8, 8);
        layout.fill = GridBagConstraints.HORIZONTAL;


        JLabel firstName = new JLabel("First Name *");
        JLabel lastName = new JLabel("Last Name *");
        JLabel email = new JLabel("Email *");
        JLabel phoneNumber = new JLabel("Phone Number");
        
        layout.insets = new Insets(10, 10, 10, 10);
        layout.gridx = 0;
        layout.gridy = 0;
        layout.gridwidth = 1;
        panel.add(firstName, layout);
        layout.gridy = 1;
        panel.add(lastName, layout);
        layout.gridy = 2;
        panel.add(email, layout);
        layout.gridy = 3;
        panel.add(phoneNumber, layout);

        JTextField fNameField = new JTextField(20);
        JTextField lNameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField phoneNumberField = new JTextField(20);

        layout.gridx = 1;
        layout.gridy = 0;
        panel.add(fNameField, layout);
        layout.gridy = 1;
        panel.add(lNameField, layout);
        layout.gridy = 2;
        panel.add(emailField, layout);
        layout.gridy = 3;
        panel.add(phoneNumberField, layout);

        JButton save = new JButton("Save");
        save.addActionListener(e -> {
            String fName = fNameField.getText();
            String lName = lNameField.getText();
            String emailInput = emailField.getText().trim();
            String phoneNumberInput = phoneNumberField.getText().trim();
            if(fName.isEmpty() || lName.isEmpty()|| emailInput.isEmpty()){
                JOptionPane.showMessageDialog(null, "All required fields must be first first.", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }else if(!phoneNumberInput.isEmpty()){
                phoneNumberInput = checkPhoneNumber(phoneNumberInput);
                if(phoneNumberInput == null) return;
            }
            boolean successful = insert.insertIntoInvestor(fName, lName, emailInput, phoneNumberInput);
            if(successful){
                JOptionPane.showMessageDialog(null, "Insertion was successful!", "Error", JOptionPane.INFORMATION_MESSAGE);
                Window w = SwingUtilities.getWindowAncestor(panel);
                w.dispose();
            }
            else JOptionPane.showMessageDialog(null, "Input is not valid. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
        });

        layout.gridx = 0;
        layout.gridy = 4;
        layout.gridwidth = 2;
        layout.anchor = GridBagConstraints.CENTER;
        layout.fill = GridBagConstraints.NONE;
        panel.add(save, layout);
        return panel;
    }

    /**
     * 
     * @return a panel to insert company
     */
    private JPanel insertCompanyPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(152, 193, 217));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        GridBagConstraints layout = new GridBagConstraints();
        layout.insets = new Insets(8, 8, 8, 8);
        layout.fill = GridBagConstraints.HORIZONTAL;


        JLabel name = new JLabel("company Name *");
        JLabel industry = new JLabel("Industry *");
        JLabel headquarters = new JLabel("Headquarters *");
        JLabel foundedYear = new JLabel("Founded Year");
        
        layout.insets = new Insets(10, 10, 10, 10);
        layout.gridx = 0;
        layout.gridy = 0;
        layout.gridwidth = 1;
        panel.add(name, layout);
        layout.gridy = 1;
        panel.add(industry, layout);
        layout.gridy = 2;
        panel.add(headquarters, layout);
        layout.gridy = 3;
        panel.add(foundedYear, layout);

        JTextField nameField = new JTextField(20);
        JTextField industryField = new JTextField(20);
        JTextField headQField = new JTextField(20);
        JTextField yearField = new JTextField(20);

        layout.gridx = 1;
        layout.gridy = 0;
        panel.add(nameField, layout);
        layout.gridy = 1;
        panel.add(industryField, layout);
        layout.gridy = 2;
        panel.add(headQField, layout);
        layout.gridy = 3;
        panel.add(yearField, layout);

        JButton save = new JButton("Save");
        save.addActionListener(e -> {
            String nameInput = nameField.getText();
            String industryInput = industryField.getText();
            String headQInput = headQField.getText().trim();
            String yearInput = yearField.getText().trim();
            if(nameInput.isEmpty() || industryInput.isEmpty()|| headQInput.isEmpty()){
                JOptionPane.showMessageDialog(null, "All required fields must be first first.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Integer year = null;

            if (!yearInput.isEmpty()) {
                if (!yearInput.matches("\\d{4}")) {
                    JOptionPane.showMessageDialog(null, "Founded year must be exactly 4 digits.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                year = Integer.parseInt(yearInput);
            }
            
            boolean successful = insert.insertIntoCompany(nameInput, industryInput, headQInput, year);
            if(successful){
                JOptionPane.showMessageDialog(null, "Insertion was successful!", "Successful", JOptionPane.INFORMATION_MESSAGE);
                Window w = SwingUtilities.getWindowAncestor(panel);
                w.dispose();
            }
            else JOptionPane.showMessageDialog(null, "Invalid input. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
        });

        layout.gridx = 0;
        layout.gridy = 4;
        layout.gridwidth = 2;
        layout.anchor = GridBagConstraints.CENTER;
        layout.fill = GridBagConstraints.NONE;
        panel.add(save, layout);
        return panel;
    }

    /**
     * 
     * @return a panel to inert stock
     */
    private JPanel insertStockPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(152, 193, 217));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        GridBagConstraints layout = new GridBagConstraints();
        layout.insets = new Insets(8, 8, 8, 8);
        layout.fill = GridBagConstraints.HORIZONTAL;


        JLabel name = new JLabel("company Name *");
        JLabel symbol = new JLabel("Ticker Symbol *");
        JLabel exchangeName = new JLabel("Exhange Name *");
        JLabel price = new JLabel("Current Price *");
        
        layout.insets = new Insets(10, 10, 10, 10);
        layout.gridx = 0;
        layout.gridy = 0;
        layout.gridwidth = 1;
        panel.add(name, layout);
        layout.gridy = 1;
        panel.add(symbol, layout);
        layout.gridy = 2;
        panel.add(exchangeName, layout);
        layout.gridy = 3;
        panel.add(price, layout);

        ArrayList<String> companies = insert.getAllCompanies();
        JComboBox nameField = new JComboBox<>(companies.toArray(new String[0]));
        nameField.setSelectedIndex(0);
        JTextField symbolField = new JTextField(20);
        JTextField exchangeNameField = new JTextField(20);
        JTextField priceField = new JTextField(20);

        layout.gridx = 1;
        layout.gridy = 0;
        panel.add(nameField, layout);
        layout.gridy = 1;
        panel.add(symbolField, layout);
        layout.gridy = 2;
        panel.add(exchangeNameField, layout);
        layout.gridy = 3;
        panel.add(priceField, layout);

        JButton save = new JButton("Save");
        save.addActionListener(e -> {
            String nameInput =  (String) nameField.getSelectedItem();
            String symbolInput = symbolField.getText();
            String exhangeNameInput = exchangeNameField.getText().trim();
            String priceInput = priceField.getText().trim();
            if(nameInput == null || nameInput.isEmpty() ||symbolInput.isEmpty() || exhangeNameInput.isEmpty() || priceInput.isEmpty()){
                JOptionPane.showMessageDialog(null, "All required fields must be first first.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double p;

            try {
                p = Double.parseDouble(priceInput);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    null,
                    "Current Price must be a number.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            boolean successful = insert.insertIntoStock(nameInput, symbolInput, exhangeNameInput, p);
            if(successful){
                JOptionPane.showMessageDialog(null, "Insertion was successful!", "Successful", JOptionPane.INFORMATION_MESSAGE);
                Window w = SwingUtilities.getWindowAncestor(panel);
                w.dispose();
            }
            else JOptionPane.showMessageDialog(null, "Invalid input. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
        });

        layout.gridx = 0;
        layout.gridy = 4;
        layout.gridwidth = 2;
        layout.anchor = GridBagConstraints.CENTER;
        layout.fill = GridBagConstraints.NONE;
        panel.add(save, layout);
        return panel;
    }

    /**
     * 
     * @return a panel to update investor
     */
    private JPanel updateInvestorPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(152, 193, 217));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        GridBagConstraints layout = new GridBagConstraints();
        layout.insets = new Insets(8, 8, 8, 8);
        layout.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("Investor ID *");
        JLabel firstName = new JLabel("First Name *");
        JLabel lastName = new JLabel("Last Name *");
        JLabel email = new JLabel("Email *");
        JLabel phoneNumber = new JLabel("Phone Number");

        JTextField idField = new JTextField(20);
        JTextField fNameField = new JTextField(20);
        JTextField lNameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField phoneField = new JTextField(20);

        JButton search = new JButton("Search");
        JButton update = new JButton("Update");

        fNameField.setEnabled(false);
        lNameField.setEnabled(false);
        emailField.setEnabled(false);
        phoneField.setEnabled(false);
        update.setEnabled(false);

        JLabel[] labels = {idLabel, firstName, lastName, email, phoneNumber};
        JTextField[] fields = {idField, fNameField, lNameField, emailField, phoneField};

        for (int i = 0; i < labels.length; i++) {
            layout.gridx = 0;
            layout.gridy = i;
            panel.add(labels[i], layout);

            layout.gridx = 1;
            panel.add(fields[i], layout);
        }

        layout.gridx = 2;
        layout.gridy = 0;
        panel.add(search, layout);

        layout.gridx = 0;
        layout.gridy = 5;
        layout.gridwidth = 3;
        layout.anchor = GridBagConstraints.CENTER;
        layout.fill = GridBagConstraints.NONE;
        panel.add(update, layout);

        search.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());

                String[] investor = modify.findInvestorById(id);

                if (investor == null) {
                    JOptionPane.showMessageDialog(null, "Investor not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                fNameField.setText(investor[1]);
                lNameField.setText(investor[2]);
                emailField.setText(investor[3]);
                phoneField.setText(investor[4]);

                fNameField.setEnabled(true);
                lNameField.setEnabled(true);
                emailField.setEnabled(true);
                phoneField.setEnabled(true);
                update.setEnabled(true);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Investor ID must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        update.addActionListener(e -> {
            String fName = fNameField.getText().trim();
            String lName = lNameField.getText().trim();
            String emailInput = emailField.getText().trim();
            String phoneInput = phoneField.getText().trim();

            if (fName.isEmpty() || lName.isEmpty() || emailInput.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All required fields must be filled first.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!phoneInput.isEmpty()) {
                phoneInput = checkPhoneNumber(phoneInput);
                if (phoneInput == null) return;
            }

            int id = Integer.parseInt(idField.getText().trim());

            boolean successful = modify.updateInvestor(id, fName, lName, emailInput, phoneInput);

            if (successful) {
                JOptionPane.showMessageDialog(null, "Investor updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                Window w = SwingUtilities.getWindowAncestor(panel);
                w.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Update failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    /**
     * 
     * @return a panel to update company
     */
    private JPanel updateCompanyPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(152, 193, 217));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        GridBagConstraints layout = new GridBagConstraints();
        layout.insets = new Insets(8, 8, 8, 8);
        layout.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("Company ID *");
        JLabel name = new JLabel("Company Name *");
        JLabel industry = new JLabel("Industry *");
        JLabel headquarters = new JLabel("Headquarters *");
        JLabel foundedYear = new JLabel("Founded Year");

        JTextField idField = new JTextField(20);
        JTextField nameField = new JTextField(20);
        JTextField industryField = new JTextField(20);
        JTextField headquartersField = new JTextField(20);
        JTextField yearField = new JTextField(20);

        JButton search = new JButton("Search");
        JButton update = new JButton("Update");

        nameField.setEnabled(false);
        industryField.setEnabled(false);
        headquartersField.setEnabled(false);
        yearField.setEnabled(false);
        update.setEnabled(false);

        JLabel[] labels = {idLabel, name, industry, headquarters, foundedYear};
        JTextField[] fields = {idField, nameField, industryField, headquartersField, yearField};

        for (int i = 0; i < labels.length; i++) {
            layout.gridx = 0;
            layout.gridy = i;
            panel.add(labels[i], layout);

            layout.gridx = 1;
            panel.add(fields[i], layout);
        }

        layout.gridx = 2;
        layout.gridy = 0;
        panel.add(search, layout);

        layout.gridx = 0;
        layout.gridy = 5;
        layout.gridwidth = 3;
        layout.anchor = GridBagConstraints.CENTER;
        layout.fill = GridBagConstraints.NONE;
        panel.add(update, layout);

        search.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());

                String[] company = modify.findCompanyById(id);

                if (company == null) {
                    JOptionPane.showMessageDialog(null, "Company not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                nameField.setText(company[1]);
                industryField.setText(company[2]);
                headquartersField.setText(company[3]);
                yearField.setText(company[4] == null ? "" : company[4]);

                nameField.setEnabled(true);
                industryField.setEnabled(true);
                headquartersField.setEnabled(true);
                yearField.setEnabled(true);
                update.setEnabled(true);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Company ID must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        update.addActionListener(e -> {
            String nameInput = nameField.getText().trim();
            String industryInput = industryField.getText().trim();
            String headquartersInput = headquartersField.getText().trim();
            String yearInput = yearField.getText().trim();

            if (nameInput.isEmpty() || industryInput.isEmpty() || headquartersInput.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All required fields must be filled first.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Integer year = null;

            if (!yearInput.isEmpty()) {
                if (!yearInput.matches("\\d{4}")) {
                    JOptionPane.showMessageDialog(null, "Founded year must be exactly 4 digits.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                year = Integer.parseInt(yearInput);
            }

            int id = Integer.parseInt(idField.getText().trim());

            boolean successful = modify.updateCompany(id, nameInput, industryInput, headquartersInput, year);

            if (successful) {
                JOptionPane.showMessageDialog(null, "Company updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                Window w = SwingUtilities.getWindowAncestor(panel);
                w.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Update failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    /**
     * 
     * @return a panel to update stock
     */
    private JPanel updateStockPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(152, 193, 217));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        GridBagConstraints layout = new GridBagConstraints();
        layout.insets = new Insets(8, 8, 8, 8);
        layout.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("Stock ID *");
        JLabel company = new JLabel("Company *");
        JLabel symbol = new JLabel("Ticker Symbol *");
        JLabel exchangeName = new JLabel("Exchange Name *");
        JLabel price = new JLabel("Current Price *");

        JTextField idField = new JTextField(20);

        ArrayList<String> companies = insert.getAllCompanies();
        JComboBox<String> companyBox = new JComboBox<>(companies.toArray(new String[0]));

        JTextField symbolField = new JTextField(20);
        JTextField exchangeField = new JTextField(20);
        JTextField priceField = new JTextField(20);

        JButton search = new JButton("Search");
        JButton update = new JButton("Update");

        companyBox.setEnabled(false);
        symbolField.setEnabled(false);
        exchangeField.setEnabled(false);
        priceField.setEnabled(false);
        update.setEnabled(false);

        layout.gridx = 0; layout.gridy = 0; panel.add(idLabel, layout);
        layout.gridx = 1; panel.add(idField, layout);

        layout.gridx = 0; layout.gridy = 1; panel.add(company, layout);
        layout.gridx = 1; panel.add(companyBox, layout);

        layout.gridx = 0; layout.gridy = 2; panel.add(symbol, layout);
        layout.gridx = 1; panel.add(symbolField, layout);

        layout.gridx = 0; layout.gridy = 3; panel.add(exchangeName, layout);
        layout.gridx = 1; panel.add(exchangeField, layout);

        layout.gridx = 0; layout.gridy = 4; panel.add(price, layout);
        layout.gridx = 1; panel.add(priceField, layout);

        layout.gridx = 2;
        layout.gridy = 0;
        panel.add(search, layout);

        layout.gridx = 0;
        layout.gridy = 5;
        layout.gridwidth = 3;
        layout.anchor = GridBagConstraints.CENTER;
        layout.fill = GridBagConstraints.NONE;
        panel.add(update, layout);

        search.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());

                String[] stock = modify.findStockById(id);

                if (stock == null) {
                    JOptionPane.showMessageDialog(null, "Stock not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                companyBox.setSelectedItem(stock[1]);
                symbolField.setText(stock[2]);
                exchangeField.setText(stock[3]);
                priceField.setText(stock[4]);

                companyBox.setEnabled(true);
                symbolField.setEnabled(true);
                exchangeField.setEnabled(true);
                priceField.setEnabled(true);
                update.setEnabled(true);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Stock ID must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        update.addActionListener(e -> {
            String companyName = (String) companyBox.getSelectedItem();
            String symbolInput = symbolField.getText().trim();
            String exchangeInput = exchangeField.getText().trim();
            String priceInput = priceField.getText().trim();

            if (companyName == null || companyName.isEmpty() || symbolInput.isEmpty() || exchangeInput.isEmpty() || priceInput.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All required fields must be filled first.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double currentPrice;

            try {
                currentPrice = Double.parseDouble(priceInput);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Current price must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = Integer.parseInt(idField.getText().trim());

            boolean successful = modify.updateStock(id, companyName, symbolInput, exchangeInput, currentPrice);

            if (successful) {
                JOptionPane.showMessageDialog(null, "Stock updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                Window w = SwingUtilities.getWindowAncestor(panel);
                w.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Update failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

    /**
     * 
     * @return
     */
    private JPanel queryTransactionsByInvestorPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(152, 193, 217));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBackground(new Color(152, 193, 217));

        GridBagConstraints layout = new GridBagConstraints();
        layout.insets = new Insets(8, 8, 8, 8);
        layout.fill = GridBagConstraints.HORIZONTAL;

        ArrayList<String> investors = query.getAllInvestors();

        JComboBox<String> investorBox = new JComboBox<>(investors.toArray(new String[0]));
        JComboBox<String> tradeTypeBox = new JComboBox<>(new String[] {"All", "BUY", "SELL"});
        JComboBox<String> sortBox = new JComboBox<>(new String[] {
            "Date newest first",
            "Date oldest first",
            "Quantity high to low"
        });

        JButton runButton = new JButton("Run Query");
        JLabel countLabel = new JLabel("Rows: 0");

        layout.gridx = 0;
        layout.gridy = 0;
        filterPanel.add(new JLabel("Investor:"), layout);

        layout.gridx = 1;
        filterPanel.add(investorBox, layout);

        layout.gridx = 0;
        layout.gridy = 1;
        filterPanel.add(new JLabel("Trade Type:"), layout);

        layout.gridx = 1;
        filterPanel.add(tradeTypeBox, layout);

        layout.gridx = 0;
        layout.gridy = 2;
        filterPanel.add(new JLabel("Sort:"), layout);

        layout.gridx = 1;
        filterPanel.add(sortBox, layout);

        layout.gridx = 2;
        layout.gridy = 0;
        filterPanel.add(runButton, layout);

        layout.gridy = 1;
        filterPanel.add(countLabel, layout);

        String[] columns = {
            "Trade Date",
            "Trade Type",
            "Ticker Symbol",
            "Company Name",
            "Industry",
            "Quantity",
            "Price Per Share"
        };

        JTable table = new JTable(new Object[0][0], columns);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(850, 300));

        runButton.addActionListener(e -> {
            String investorChoice = (String) investorBox.getSelectedItem();
            String tradeType = (String) tradeTypeBox.getSelectedItem();
            String sortOption = (String) sortBox.getSelectedItem();

            if (investorChoice == null || investorChoice.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please select an investor.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Object[][] data = query.getTransactionsByInvestor(investorChoice, tradeType, sortOption);

            table.setModel(new javax.swing.table.DefaultTableModel(data, columns));

            countLabel.setText("Rows: " + data.length);
        });

        panel.add(filterPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * 
     * @return
     */
    private JPanel queryTopStocksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(152, 193, 217));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBackground(new Color(152, 193, 217));

        GridBagConstraints layout = new GridBagConstraints();
        layout.insets = new Insets(8, 8, 8, 8);
        layout.fill = GridBagConstraints.HORIZONTAL;

        ArrayList<String> industries = query.getAllIndustries();

        JComboBox<String> industryBox = new JComboBox<>(industries.toArray(new String[0]));
        JComboBox<String> topNBox = new JComboBox<>(new String[] {"5", "15", "25", "All"});

        JButton runButton = new JButton("Run Query");
        JLabel countLabel = new JLabel("Rows: 0");

        layout.gridx = 0;
        layout.gridy = 0;
        filterPanel.add(new JLabel("Industry:"), layout);

        layout.gridx = 1;
        filterPanel.add(industryBox, layout);

        layout.gridx = 0;
        layout.gridy = 1;
        filterPanel.add(new JLabel("Top N:"), layout);

        layout.gridx = 1;
        filterPanel.add(topNBox, layout);

        layout.gridx = 2;
        layout.gridy = 0;
        filterPanel.add(runButton, layout);

        layout.gridy = 1;
        filterPanel.add(countLabel, layout);

        String[] columns = {
            "Rank",
            "Ticker Symbol",
            "Company Name",
            "Industry",
            "Total Shares Traded",
            "Number of Transactions"
        };

        JTable table = new JTable(new Object[0][0], columns);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(850, 300));

        runButton.addActionListener(e -> {
            String industry = (String) industryBox.getSelectedItem();
            String topN = (String) topNBox.getSelectedItem();

            Object[][] data = query.getTopStocksByVolume(industry, topN);

            table.setModel(new javax.swing.table.DefaultTableModel(data, columns));

            countLabel.setText("Rows: " + data.length);
        });

        panel.add(filterPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }


    /**
     * 
     * @param title
     * @return
     */
    public JLabel makeSectionTitle(String title){
        JLabel label = new JLabel(title);
        label.setOpaque(true);
        label.setBackground(new Color(9, 59, 90));
        label.setPreferredSize(new Dimension(0, 40));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(Color.white);
        return label;
    }

    /**
     * 
     * @param title
     * @return
     */
    public JButton styleButton(String title){
        JButton button = new JButton(title);
        button.setBackground(Color.white);
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                button.setBackground(new Color(9, 59, 90));
                button.setForeground(Color.white);
            }

            public void mouseExited(MouseEvent e){
                button.setBackground(Color.white);
                button.setForeground(Color.black);
            }
        });
        return button;
    }

    /**
     * 
     * @param number
     * @return
     */
    public String checkPhoneNumber(String number){
        if(number.matches("\\d{10}")) {
            return number.substring(0,3) + "-"
                + number.substring(3,6) + "-"
                + number.substring(6);
        }else if(number.matches("\\d{3}-\\d{3}-\\d{4}")) return number;
        else{
            JOptionPane.showMessageDialog(null, "Phone Number is not valid", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
