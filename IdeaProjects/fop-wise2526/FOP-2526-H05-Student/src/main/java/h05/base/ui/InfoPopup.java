package h05.base.ui;

import fopbot.World;
import h05.base.mineable.Inventory;
import h05.entity.Miner;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;


/**
 * A popup dialog that displays information about the mining inventory.
 *
 * @author Nhan Huynh, Nico Schnieders
 * @see Miner
 */
@DoNotTouch
public class InfoPopup extends JDialog {

    /**
     * The names of the columns in the info table.
     */
    @DoNotTouch
    private final String[] columnNames = {"Name", "Amount"};

    /**
     * The data to be displayed in the info table, where each row contains the name and amount of an item.
     */
    @DoNotTouch
    private final Object[][] data;

    /**
     * Constructs a new {@link InfoPopup} instance with the specified parent frame and data.
     *
     * @param parent the parent frame for this dialog
     * @param data   the data to be displayed in the info table, where each row contains the name and amount of an item
     */
    @DoNotTouch
    public InfoPopup(JFrame parent, Object[][] data) {
        super(parent, "Mining info", true);
        this.data = data;
        DefaultTableModel tableModel = new DefaultTableModel(this.data, columnNames);
        JTable table = new JTable(tableModel);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int x = 0; x < columnNames.length; x++) {
            table.getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
        }
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Displays the inventory information in a popup dialog.
     *
     * @param inventory the inventory to display
     */
    @DoNotTouch
    public static void showInfo(Inventory inventory) {
        Object[][] data = new Object[inventory.size()][2];
        String[] names = inventory.getNames();
        for (int i = 0; i < inventory.size(); i++) {
            data[i][0] = names[i];
            data[i][1] = inventory.getAmount(names[i]);
        }
        JFrame parent = World.getGlobalWorld().getGuiFrame();

        InfoPopup infoPopup = new InfoPopup(parent, data);
        infoPopup.setSize(300, 200);
        infoPopup.setLocationRelativeTo(parent);
        infoPopup.setVisible(true);
    }

    /**
     * Returns the names of the columns in the info table.
     *
     * @return an array of column names
     */
    @DoNotTouch
    public String[] getColumnNames() {
        return columnNames;
    }

    /**
     * Returns the data to be displayed in the info table.
     *
     * @return a 2D array of data where each row contains the name and amount of an item
     */
    @DoNotTouch
    public Object[][] getData() {
        return data;
    }
}
