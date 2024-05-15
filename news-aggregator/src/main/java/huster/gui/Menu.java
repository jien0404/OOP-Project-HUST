package huster.gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame {
    private static final long serialVersionUID = 1L;
    public static final int X = 1440;
    public static final int Y = 1024;
    public static final int ORIGIN_X = 100;
    public static final int ORIGIN_Y = 100;

    private int seeMoreButtonClickedCount = 0;
    private JButton articleButton;
    private JLabel articleLabel;
    private JPanel small_articlePanel;
    private JPanel labelPanel;
    private JPanel articlePanel;
    private ImageIcon articleIcon;
   
    Header menu = new Header();

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
        
        menu.addSearchButtonListener(new ActionListener() {
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

        articleIcon = new ImageIcon("news-aggregator\\resource\\assets\\articleIcon.png");
        ImageIcon BigarticleIcon = new ImageIcon("news-aggregator\\resource\\assets\\BigarticleIcon.png");

      
        JPanel toparticlePanel = new JPanel();
        toparticlePanel.setPreferredSize(new Dimension(1280,625));
        toparticlePanel.setLayout(new BorderLayout());

        JPanel gapPanel = new JPanel();
        gapPanel.setPreferredSize(new Dimension(1280,75));

        JButton bigArticleButton = new JButton(BigarticleIcon);
        bigArticleButton.setBackground(GREY_menu);
        bigArticleButton.setOpaque(false);
        bigArticleButton.setContentAreaFilled(false);
        bigArticleButton.setBorderPainted(false);
        bigArticleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(bigArticleButton);
                News news = new News();
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
        toparticlePanel.add(gapPanel,BorderLayout.SOUTH);

        articlePanel = new JPanel();
        articlePanel.setPreferredSize(new Dimension(1280, 1500));
        articlePanel.setLayout(new GridLayout(6,2,175,0));
        createSmall_articlePanel(6);

        
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
                seeMoreButtonClickedCount++;
                fullarticlePanel.setPreferredSize(new Dimension(1280, 2500 + 1200 * seeMoreButtonClickedCount));
                articlePanel.setPreferredSize(new Dimension(1280, 1500 + 1200 * seeMoreButtonClickedCount));
                articlePanel.setLayout(new GridLayout(6 + 3 * seeMoreButtonClickedCount,2,175,0));
                createSmall_articlePanel(3);
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
        contentPane.add(menu, BorderLayout.NORTH);
        // contentPane.add(toparticlePanel, BorderLayout.CENTER);

        contentPane.add(scrollPane_suggested,BorderLayout.CENTER);  
    
        // this.pack();        
        
    }

    public void createSmall_articlePanel(int numberOfRows) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < numberOfRows; j++) {
                articleButton = new JButton(articleIcon);
                // articleButton.setBackground(GREY_menu);
                articleButton.setContentAreaFilled(false);
                articleButton.setBorderPainted(false);
                articleButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        News news = new News();
                        news.setVisible(true);
                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(menu);
                        ScreenHistory.getInstance().pushScreen(frame);
                        dispose();
                    }
                });

                articleLabel = new JLabel("Be Xuan Mai");
                articleLabel.setHorizontalAlignment(JLabel.CENTER);
                articleLabel.setVerticalAlignment(JLabel.CENTER);

                small_articlePanel = new JPanel();
                small_articlePanel.setLayout(new BoxLayout(small_articlePanel, BoxLayout.Y_AXIS));
                small_articlePanel.add(articleButton);

                labelPanel = new JPanel();
                labelPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
                labelPanel.add(articleLabel);
                small_articlePanel.add(labelPanel);

                small_articlePanel.setPreferredSize(new Dimension(465, 180));

                articlePanel.add(small_articlePanel);
            }
        }

    }

    public void addBackButton(){
        menu.addBackButtonForMenu();
    }
}


