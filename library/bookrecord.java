import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class bookrecord extends Frame
{
	Label l1 = new Label("Book Record");
	Label l2 = new Label("Book ID");
	Label l3 = new Label("Title");
	Label l4 = new Label("Author");
	Label l6 = new Label("Quantity");
	Label l5 = new Label("-");
	Label lblid = new Label("-");
	//TextField lblid = new TextField();

	TextField txttitle = new TextField();
	TextField txtauthor = new TextField();
	TextField txtqty = new TextField();

	////////edit from here////////
	 
	Button btnsave = new Button("SAVE");
	Button btnnew = new Button("NEW");
	Button btnclose = new Button("CLOSE");

	bookrecord()
	{
		super("Library Books Entry");
		setLayout( null );

		l1.setBounds( 80, 50, 200 , 30 );
		
		l2.setBounds( 50, 100, 80 , 30 );
		lblid.setBounds( 150, 100, 80 , 30 );
		
		l3.setBounds( 50, 150, 80 , 30 );
		txttitle.setBounds( 150, 150, 200 , 30 );
		txttitle.setEditable(false);

		l4.setBounds( 50, 200, 80 , 30 );
		txtauthor.setBounds( 150, 200, 200 , 30 );
		txtauthor.setEditable(false);

		l6.setBounds(50,250,80,30);
		txtqty.setBounds(150,250,80,30);
		txtqty.setEditable(false);

		l5.setBounds( 50, 300, 400 , 30 );

		btnsave.setBounds( 50, 350, 50 , 30 );
		btnsave.setVisible(false);
		btnsave.setBackground(Color.yellow);
		
		btnnew.setBounds( 200, 350, 50 , 30 );
		btnnew.setBackground(Color.yellow);

		btnclose.setBounds( 350, 350, 50 , 30 );
		btnclose.setBackground(Color.yellow);


		Font f = new Font("Arial", Font.BOLD, 30);
		Font f1 = new Font("Arial", Font.BOLD, 20);

		l1.setFont( f );
		l1.setForeground(Color.red);
		l5.setFont(f1);
		l5.setForeground(Color.red);
		l5.setBackground(Color.yellow);

		

		add(l1);add(l2);add(l3);add(l4);add(l5);add(l6);
		add(lblid);add(txttitle);add(txtauthor);add(txtqty);
		add(btnsave);add(btnclose);add(btnnew);
		
		btnsave.addActionListener( new BtnListener());
		btnnew.addActionListener( new BtnListener());
		btnclose.addActionListener( new BtnListener());
		
		setSize( 500, 500 );
		setVisible(true);

	}

	void clearFields()
	{
		lblid.setText("");
		txttitle.setText("");
		txtauthor.setText("");
		txtqty.setText("");
		l5.setText("-");
		txttitle.requestFocus();
	}

	class BtnListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String s=e.getActionCommand();

			if(s.equals("SAVE"))
			{				
				Connection con=null;				
				Statement st=null;

				try
				{
				Class.forName("com.mysql.jdbc.Driver");
				con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");
				st=con.createStatement();
				int id=Integer.parseInt(lblid.getText());
				String ti=txttitle.getText();
				String au=txtauthor.getText();
				int qty=Integer.parseInt(txtqty.getText());
				String str="insert into bookrecord  values("+id+",'"+ti+"','"+au+"',"+qty+")";
				
				st.executeUpdate(str);
				 
				l5.setText("Book record added.. !");

				}
				catch(Exception ea)
				{
				l5.setText("Error .." +ea);
				}

				finally
				{
				try
				{
				con.close();
				}
				catch(Exception ex)
				{ }
				}

			}

			else if(s.equals("NEW"))
			{
				
				btnsave.setVisible(true);
				txttitle.setEditable(true);
				txtqty.setEditable(true);
				txtauthor.setEditable(true);
				clearFields();
				Connection con=null;
				Statement st=null;

				try
				{
					Class.forName("com.mysql.jdbc.Driver");
					con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");
				    st=con.createStatement();
		
					String qry = "Select max(bookid)+1 as x from bookrecord";
					ResultSet rs=st.executeQuery(qry);
					rs.next();
					if(rs.getInt("x")==0)
						lblid.setText("1");
                    else				
						lblid.setText(""+rs.getInt("x"));
				}
				
				catch(Exception ex)
				{
					l5.setText("Error"+ex);
				}
				finally
				{
					try
					{
						con.close();
					}
					catch(Exception ex)
					{
					}
				}
				
			}
			else if(s.equals("CLOSE"))
			{
				dispose();
			}
			
		}
	}

	public static void main(String[ ] args )
	{
		bookrecord c = new bookrecord();
	}

}	