import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Calendar;

class BookIssue extends Frame
{
	Label lbl1=new Label("ID");
	Label lbl2=new Label("Quantity");
	Label lblid=new Label("--");
	Label lblqty=new Label("--");
	Choice chtitle = new Choice();
	Choice chauthor = new Choice();
	Label lbl4=new Label("Author");
	Label lbl3=new Label("Title");
	Label lbldate=new Label("");
	Label lbl5=new Label("Student Id");
	TextField txtsdid=new TextField();
	Button btnsave=new Button("Save");
	Button btnnew=new Button("New");
	Button btnclose=new Button("Close");
	Label lblmsg = new Label("");
	
	BookIssue()
	{
		super("Book Issue");
		setLayout(null);
		lbl3.setBounds(30,60,100,30);
		chtitle.setBounds(160,60,100,30);
		lbl4.setBounds(30,110,100,30);
		chauthor.setBounds(160,110,100,30);
		lbldate.setBounds(300,30,100,70);
		lbl1.setBounds(30,160,50,30);
		lbl2.setBounds(200,160,50,30);
		lblid.setBounds(100,160,50,30);
		lblqty.setBounds(270,160,50,30);
		lbl5.setBounds(30,210,100,30);
		txtsdid.setBounds(160,210,100,30);
		btnsave.setBounds(30,310,50,30);
		btnnew.setBounds(150,310,50,30);
		btnclose.setBounds(270,310,50,30);
		lblmsg.setBounds(30,400,200,80 );
		Font f = new Font("Arial", Font.BOLD, 14);
		lblmsg.setFont( f );
		
		add(lbl1);
		add(lbl2);
		add(lbl3);
		add(lbl4);
		add(lbl5);
		add(lblid);
		add(lblqty);
		add(lbldate);
		add(chtitle);
		add(chauthor);
		add(txtsdid);
		add(btnsave);
		add(btnclose);
		add(btnnew);
		add(lblmsg);
	
		Calendar cal =Calendar.getInstance();
		String	dt="";
		dt=dt+cal.get(Calendar.DATE)+"/";
		dt=dt+(cal.get(Calendar.MONTH)+1)+"/";
		dt=dt+cal.get(Calendar.YEAR);
		lbldate.setText(dt);
		
		Connection con=null;
				try
				{
					Class.forName("com.mysql.jdbc.Driver");
					con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");
					Statement st=con.createStatement();
					String s="Select distinct(title) from bookrecord order by title";
					ResultSet rs=st.executeQuery(s);
						chtitle.add("-");
					while(rs.next())
						chtitle.add(rs.getString("title"));
				}
				catch(Exception ex)
				{
					lblmsg.setText("Error"+ex);
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
			chtitle.addItemListener( new ListListener() );
			chauthor.addItemListener( new AuthorListener() );
		/*BtnListener x=new BtnListener();
		btnsave.addActionListener(x);
		btnnew.addActionListener(x);	
		btnclose.addActionListener(x);*/
		setSize(500,500);
		setVisible(true);
	}
	
	class ListListener implements ItemListener
	{
		public void itemStateChanged( ItemEvent ie)
		{
			chauthor.removeAll();
			Connection con=null;
				try
				{
					String ch=chtitle.getSelectedItem();
					Class.forName("com.mysql.jdbc.Driver");
					con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");
					Statement st=con.createStatement();
					String s="Select author from bookrecord where title='"+ch+"'";
					ResultSet rs=st.executeQuery(s);
					chauthor.add("---Select Author");
					while(rs.next())
						chauthor.add(rs.getString("author"));
				}
				catch(Exception ex)
				{
					lblmsg.setText("Error"+ex);
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
	}
	
	class AuthorListener implements ItemListener
	{
		public void itemStateChanged( ItemEvent ie)
		{
			Connection con=null;
				try
				{
					String a =chauthor.getSelectedItem();
					String ch=chtitle.getSelectedItem();
					Class.forName("com.mysql.jdbc.Driver");
					con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");
					Statement st=con.createStatement();
					String s="Select bookid,qty from bookrecord where title='"+ch+"'And author='"+a+"'";
					ResultSet rs=st.executeQuery(s);
					rs.next();
					lblid.setText(rs.getInt("bookid")+"");
					lblqty.setText(rs.getInt("qty")+"");
					txtsdid.requestFocus();
				}
				catch(Exception ex)
				{
					lblmsg.setText("Error"+ex);
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
	}
	
	public static void main(String args[])
	{
		BookIssue g=new BookIssue(); 
	}
}