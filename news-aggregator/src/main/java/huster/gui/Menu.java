package huster.gui;

import javax.swing.*;

import com.google.gson.JsonObject;

import huster.action.GetData;
import huster.action.newsObject;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
public class Menu extends JFrame {
    private static final long serialVersionUID = 1L;
    public static final int X = 1440;
    public static final int Y = 1024;
    public static final int ORIGIN_X = 100;
    public static final int ORIGIN_Y = 100;
    

    public int number_News = 12;

    private int seeMoreButtonClickedCount = 0;
    private JButton articleButton;
    private JLabel articleLabel;
    private JPanel small_articlePanel;
    private JPanel labelPanel;
    // private JPanel articlePanel;
    private ImageIcon articleIcon;
   
    Header menu = new Header();
    // private JButton articleButton;
    // private JLabel articleLabel;
    // private JPanel small_articlePanel;
    // private JPanel labelPanel;
    

    private static JPanel articlePanel;
    // private ImageIcon articleIcon;
    // luu tru bai viet
    private List<JPanel> newsList = new ArrayList<>();
   
    Header header = new Header();

    public int getNumberNews() {
        return number_News;
    }

    public Menu() {
        ScreenHistory.getInstance();
        ScreenHistory.getInstance().pushScreen(this);
        Container contentPane = getContentPane(); // Sử dụng getContentPane() để lấy contentPane của JFrame

        setSize(X, Y);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("The MENU");
        contentPane.setLayout(new BorderLayout());

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        System.setProperty("BLACK_menu", "0x222222");
        // Color BLACK_menu = Color.getColor("BLACK_menu");
        System.setProperty("GREY_menu", "0xFFFFFF");
        Color GREY_menu = Color.getColor("GREY_menu");
        
        header.addSearchButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchUI searchUI = new SearchUI();
                searchUI.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(menu);
                ScreenHistory.getInstance().pushScreen(frame);
                dispose();
            }
        });

        menu.addBackButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!ScreenHistory.getInstance().isEmpty()) {
                    JFrame previousScreen = ScreenHistory.getInstance().popScreen();
                    previousScreen.setVisible(true);
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(menu);
                    ScreenHistory.getInstance().pushScreen(frame);
                    dispose();
                }
            }
        });

        Font font40 = new Font("ARIAL",Font.PLAIN,40);
        // ảnh để hiện lên nút bấm bài báo
        // articleIcon = new ImageIcon("news-aggregator\\resource\\assets\\articleIcon.png");
        ImageIcon BigarticleIcon = new ImageIcon("news-aggregator\\resource\\assets\\BigarticleIcon.png");

        // đặt tên kiểu chi z ???
        JPanel toparticlePanel = new JPanel();
        toparticlePanel.setPreferredSize(new Dimension(1280,625));
        toparticlePanel.setLayout(new BorderLayout());
        
        JPanel nothingPanel = new JPanel();
        nothingPanel.setPreferredSize(new Dimension(1280,75));

        JButton bigArticleButton = new JButton(BigarticleIcon);
        bigArticleButton.setBackground(GREY_menu);
        bigArticleButton.setOpaque(false);
        bigArticleButton.setContentAreaFilled(false);
        bigArticleButton.setBorderPainted(false);
        bigArticleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(bigArticleButton);
                
                News news = new News("null", "null", "null", "null", "null");
                news.setVisible(true);
                ScreenHistory.getInstance().pushScreen(frame);
                dispose();
            }
        });
        JLabel BigarticleLabel_title = new JLabel("Be Xuan Mai");
        BigarticleLabel_title.setHorizontalAlignment(JLabel.CENTER);
        BigarticleLabel_title.setVerticalAlignment(JLabel.CENTER);
        toparticlePanel.add(bigArticleButton,BorderLayout.NORTH);
        toparticlePanel.add(BigarticleLabel_title,BorderLayout.CENTER);
        // toparticlePanel.add(gapPanel,BorderLayout.SOUTH);

        articlePanel = new JPanel();
        articlePanel.setPreferredSize(new Dimension(1280, 1500));
        articlePanel.setLayout(new GridLayout(6,2,175,0));
        
        JPanel fullarticlePanel = new JPanel();
        fullarticlePanel.setPreferredSize(new Dimension(1280,2500));
        fullarticlePanel.setLayout(new BorderLayout());
        fullarticlePanel.add(toparticlePanel,BorderLayout.NORTH);
        fullarticlePanel.add(articlePanel,BorderLayout.CENTER);


        JButton seeMoreButton = new JButton("See more!");
        seeMoreButton.setFont(font40);
        seeMoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(seeMoreButtonClickedCount == 2) {
                    seeMoreButton.setVisible(false);
                    return ;
                }
                
                seeMoreButtonClickedCount++;
                number_News += 6;
                fullarticlePanel.setPreferredSize(new Dimension(1280, 2500 + 1200 * seeMoreButtonClickedCount));
                articlePanel.setPreferredSize(new Dimension(1280, 1500 + 1200 * seeMoreButtonClickedCount));
                articlePanel.setLayout(new GridLayout(6 + 3 * seeMoreButtonClickedCount,2,175,0));
                // createSmall_articlePanel(3);
                createNews();
                revalidate();
            }
        });
        fullarticlePanel.add(seeMoreButton, BorderLayout.SOUTH);

        JScrollPane scrollPane_suggested = new JScrollPane(fullarticlePanel);
        scrollPane_suggested.setPreferredSize(new Dimension(1280, 800));
        scrollPane_suggested.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane_suggested.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane_suggested.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));




        // contentPane.setBackground(GREY_menu);
        this.setBackground(GREY_menu);
        contentPane.add(header, BorderLayout.NORTH);
        // contentPane.add(toparticlePanel, BorderLayout.CENTER);

        contentPane.add(scrollPane_suggested,BorderLayout.CENTER);  
        // tao bang tin
        articlePanel.setVisible(true);
        createNews();
        // this.pack();       
        
    }

    // tao bang tin
    public void createNews(){
        List<JsonObject> _JsonObjects = new GetData().getNewsElements();
        // System.out.println(_JsonObjects.get(0).get("linkImage").getAsString());
        for(int i = 0; i < number_News; i++){
            // JPanel _JPanel = new GetData().set(HandleImage.ReadURL(_JsonObjects.get(i).get("linkImage").getAsString()), _JsonObjects.get(i).get("title").getAsString());
            JPanel _JPanel = new newsObject(_JsonObjects.get(i)).setAsJPanel();
            
            newsList.add(_JPanel);
        }

        for(int i = 0; i < number_News; i++){
            articlePanel.add(newsList.get(i));
        }

    }

    public void addBackButton(){
        menu.addBackButtonForMenu();
    }
}


//Class for generating Header
class Header extends JPanel{
    private JPanel menu = new JPanel();
    private JPanel menuLeft = new JPanel();
    private JPanel menuRight = new JPanel();
    public JButton closeButton = new JButton();
    public JButton homeButton = new JButton();
    public JButton searchButton = new JButton();
    public JButton userButton = new JButton();
    public JButton menuButton = new JButton();

    public Header(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        System.setProperty("BLACK_menu", "0x222222");
        Color BLACK_menu = Color.getColor("BLACK_menu");
        menu.setLayout(new BorderLayout());
        menu.setSize(1440, 101);
        menu.setBackground(BLACK_menu);

        menuLeft.setBackground(BLACK_menu);
        menuLeft.setLayout(new FlowLayout(FlowLayout.LEFT));

        menuRight.setBackground(BLACK_menu);
        menuRight.setLayout(new FlowLayout(FlowLayout.RIGHT));

         // Thêm các nút vào menuLeft
        // TODO
        ImageIcon menuIcon = new ImageIcon("news-aggregator\\\\resource\\\\assets\\\\menuIcon.png" );
        menuButton.setIcon(menuIcon);
        menuButton.setPreferredSize(new Dimension(50,50));
        menuButton.setBorderPainted(false);
        menuButton.setFocusPainted(false);
        menuButton.setContentAreaFilled(false);
        menuLeft.add(menuButton);
       
        ImageIcon closeIcon = new ImageIcon("news-aggregator\\resource\\assets\\closeIcon.png");
        closeButton.setIcon(closeIcon);
        closeButton.setPreferredSize(new Dimension(50, 50));
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setContentAreaFilled(false);

        ImageIcon homeIcon = new ImageIcon("news-aggregator\\resource\\assets\\homeIcon.png");
        homeButton.setIcon(homeIcon);
        homeButton.setPreferredSize(new Dimension(50, 50));
        homeButton.setBorderPainted(false);
        homeButton.setFocusPainted(false);
        homeButton.setContentAreaFilled(false);
        menuLeft.add(homeButton);

        // Thêm các nút vào menuRight
        ImageIcon searchIcon = new ImageIcon("news-aggregator\\resource\\assets\\searchIcon.png");
        searchButton.setIcon(searchIcon);
        searchButton.setPreferredSize(new Dimension(50, 50));
        searchButton.setBorderPainted(false);
        searchButton.setFocusPainted(false);
        searchButton.setContentAreaFilled(false);
        menuRight.add(searchButton);

        ImageIcon userIcon = new ImageIcon("news-aggregator\\resource\\assets\\userIcon.png");
        userButton.setIcon(userIcon);
        userButton.setPreferredSize(new Dimension(50, 50));
        userButton.setBorderPainted(false);
        userButton.setFocusPainted(false);
        userButton.setContentAreaFilled(false);
        menuRight.add(userButton);
        
        menu.add(menuLeft, BorderLayout.WEST);
        menu.add(menuRight, BorderLayout.EAST);
        this.add(menu);
    }

    public void addButtonForSearchUI(){
        menuLeft.removeAll();
        menuLeft.add(closeButton);
        menuLeft.add(homeButton);

    }

    public void addCloseButtonListener(ActionListener listener) {
        closeButton.addActionListener(listener);
    }
    
    public void addHomeButtonListener(ActionListener listener) {
        homeButton.addActionListener(listener);
    }
    
    public void addSearchButtonListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }
    
    public void addUserButtonListener(ActionListener listener) {
        userButton.addActionListener(listener);
    }

    public void addMenuButtonListener(ActionListener listener) {
        menuButton.addActionListener(listener);
    }
}
