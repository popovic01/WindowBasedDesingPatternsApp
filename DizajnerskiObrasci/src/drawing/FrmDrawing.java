package drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;

import geometry.*;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;
import javax.swing.JToggleButton;
import javax.swing.JColorChooser;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FrmDrawing extends JFrame{

	private JPanel contentPane;
	private final ButtonGroup btnGroup = new ButtonGroup();
	private final ButtonGroup btnGroup2 = new ButtonGroup();
	private PnlDrawing drawing;
	JToggleButton tglbtnPoint;
	JToggleButton tglbtnLine;
	JToggleButton tglbtnRectangle;
	JToggleButton tglbtnCircle;
	JToggleButton tglbtnDonut;
	JToggleButton tglbtnSelection;
	JToolBar toolBar;
	JButton tglbtnDelete;
	JButton tglbtnModify;
	private JButton btnDeleteAll;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmDrawing frame = new FrmDrawing();
					frame.setResizable(false);
					frame.setSize(500, 400);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmDrawing() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		setTitle("Popovic Milica IT18-2019");
		setIconImage(new ImageIcon("Icon.png").getImage());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
			
		getContentPane().setBackground(new Color(238, 238, 238));
		drawing = new PnlDrawing();
		getContentPane().add(drawing);
		
		toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		tglbtnSelection = new JToggleButton("Selection");
		tglbtnSelection.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				drawing.check(6);
				btnGroup.clearSelection();
				btnGroup2.clearSelection();
			}
		});
		toolBar.add(tglbtnSelection);
		
		tglbtnPoint = new JToggleButton("Point");
		tglbtnPoint.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				drawing.check(1);	
				btnGroup.clearSelection();
				btnGroup2.clearSelection();}
		});
		toolBar.add(tglbtnPoint);
		
		tglbtnLine = new JToggleButton("Line");
		tglbtnLine.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				drawing.check(2);
				btnGroup.clearSelection();
				btnGroup2.clearSelection();
			}
		});
		toolBar.add(tglbtnLine);
		
		tglbtnRectangle = new JToggleButton("Rectangle");
		tglbtnRectangle.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				drawing.check(3);
				btnGroup.clearSelection();
				btnGroup2.clearSelection();
			}
		});
		toolBar.add(tglbtnRectangle);
		
		tglbtnCircle = new JToggleButton("Circle");
		tglbtnCircle.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				drawing.check(4);
				btnGroup.clearSelection();
				btnGroup2.clearSelection();
			}
		});
		toolBar.add(tglbtnCircle);
		
		tglbtnDonut = new JToggleButton("Donut");
		tglbtnDonut.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				drawing.check(5);
				btnGroup.clearSelection();
				btnGroup2.clearSelection();
			}
		});
		toolBar.add(tglbtnDonut);
		
		tglbtnModify = new JButton("Modify");
		tglbtnModify.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				drawing.modify();
				btnGroup.clearSelection();
				btnGroup2.clearSelection();
				drawing.check(0);
			}
		});

		toolBar.add(tglbtnModify);
		
		tglbtnDelete = new JButton("Delete");
		tglbtnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				drawing.delete();
				btnGroup.clearSelection();
				btnGroup2.clearSelection();
				drawing.check(0);
			}
		});
		
		toolBar.add(tglbtnDelete);
		
		btnGroup2.add(tglbtnSelection);
		btnGroup.add(tglbtnPoint);
		btnGroup.add(tglbtnLine);
		btnGroup.add(tglbtnRectangle);
		btnGroup.add(tglbtnDonut);
		btnGroup.add(tglbtnCircle);
		
		btnDeleteAll = new JButton("Delete all");
		btnDeleteAll.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				drawing.deleteAll();
				btnGroup.clearSelection();
				btnGroup2.clearSelection();
				drawing.check(0);
			}
		});
		toolBar.add(btnDeleteAll);
		//btnGroup.add(tglbtnModify);
		//btnGroup.add(tglbtnDelete);
		
	}

	public JToggleButton getTglbtnPoint() {
		return tglbtnPoint;
	}

	public void setTglbtnPoint(JToggleButton tglbtnPoint) {
		this.tglbtnPoint = tglbtnPoint;
	}

	public JToggleButton getTglbtnLine() {
		return tglbtnLine;
	}

	public void setTglbtnLine(JToggleButton tglbtnLine) {
		this.tglbtnLine = tglbtnLine;
	}

	public JToggleButton getTglbtnRectangle() {
		return tglbtnRectangle;
	}

	public void setTglbtnRectangle(JToggleButton tglbtnRectangle) {
		this.tglbtnRectangle = tglbtnRectangle;
	}

	public JToggleButton getTglbtnCircle() {
		return tglbtnCircle;
	}

	public void setTglbtnCircle(JToggleButton tglbtnCircle) {
		this.tglbtnCircle = tglbtnCircle;
	}

	public JToggleButton getTglbtnDonut() {
		return tglbtnDonut;
	}

	public void setTglbtnDonut(JToggleButton tglbtnDonut) {
		this.tglbtnDonut = tglbtnDonut;
	}

	public JToggleButton getTglbtnSelection() {
		return tglbtnSelection;
	}

	public void setTglbtnSelection(JToggleButton tglbtnSelection) {
		this.tglbtnSelection = tglbtnSelection;
	}

	public JToolBar getToolBar() {
		return toolBar;
	}

	public void setToolBar(JToolBar toolBar) {
		this.toolBar = toolBar;
	}

	public ButtonGroup getBtnGroup() {
		return btnGroup;
	}

	public ButtonGroup getBtnGroup2() {
		return btnGroup2;
	}

}
