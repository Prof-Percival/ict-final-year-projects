package cm;

import javax.swing.Icon;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Christoph Percival
 */

//Create a Table Model To Display The Contacts Images in JTable
public class TableModel extends AbstractTableModel {

    private String[] columns;
    private Object[][] rows;

    public TableModel() 
    {
        columns = null;
        rows = null;
    }

    public TableModel(Object[][] rows, String[] columns) 
    {
        this.columns = columns;
        this.rows = rows;
    }
    
    @Override
    public Class getColumnClass(int col)
    {
        //The Index of the Image Column is 8
        if (col == 8)
        {
            return Icon.class;
        }
        else
        {
            return getValueAt(0, col).getClass();
        }
    }
    
    
    @Override
    public int getRowCount()
    {
        return this.rows.length;
    }

    @Override
    public int getColumnCount() 
    {
        return this.columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) 
    {
        return this.rows[rowIndex][columnIndex];
    }
    
    @Override
    public String getColumnName(int col)
    {
        return this.columns[col];
    }
    
}
