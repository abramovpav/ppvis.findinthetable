/*
 * (swing1.1beta3)
 * 
 */

package by.bsuir.iit.abramov.ppvis.findinthetable.model.table;

import java.awt.Dimension;

/**
 * @version 1.0 11/22/98
 */

public interface CellAttribute {

	public void addColumn();

	public void addRow();

	public Dimension getSize();

	public void insertRow(int row);

	public void setSize(Dimension size);

}
