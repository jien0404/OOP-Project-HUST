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
    private static JPanel articlePanel;

    // luu tru bai viet
    private List<JPanel> newsList = new ArrayList<>();
   
    Header menu = new Header();

    public int getNumberNews() {
        return number_News;
    }

    public Menu() {
        Container contentPane = getContentPane(); // Sử dụng getContentPane() để lấy contentPane của JFrame
        menu.addButtonForMenu();

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
                new SearchUI().setVisible(true);
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

        Font font30 = new Font("ARIAL",Font.PLAIN,30);
        ImageIcon toparticleIcon = new ImageIcon("news-aggregator\\resource\\assets\\BigarticleIcon.png");

        JPanel toparticlePanel = new JPanel();
        toparticlePanel.setPreferredSize(new Dimension(1280,440));
        toparticlePanel.setLayout(new BorderLayout());

        JButton topArticleButton = new JButton(toparticleIcon);
        topArticleButton.setBackground(GREY_menu);
        topArticleButton.setOpaque(false);
        topArticleButton.setContentAreaFilled(false);
        topArticleButton.setBorderPainted(false);
        topArticleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(topArticleButton);
                
                News news = new News("null", "null", "null", "null", "null");
                news.setVisible(true);
                ScreenHistory.getInstance().pushScreen(frame);
                dispose();
            }
        });
        JLabel toparticleLabel_title = new JLabel("null");
        toparticleLabel_title.setHorizontalAlignment(JLabel.CENTER);
        toparticleLabel_title.setVerticalAlignment(JLabel.CENTER);
        toparticlePanel.add(topArticleButton,BorderLayout.NORTH);
        toparticlePanel.add(toparticleLabel_title,BorderLayout.CENTER);

        articlePanel = new JPanel();
        articlePanel.setPreferredSize(new Dimension(1280, 1500));
        articlePanel.setLayout(new GridLayout(6,2,175,0));
        
        JPanel fullarticlePanel = new JPanel();
        fullarticlePanel.setPreferredSize(new Dimension(1280,2500));
        fullarticlePanel.setLayout(new BorderLayout());
        fullarticlePanel.add(toparticlePanel,BorderLayout.NORTH);
        fullarticlePanel.add(articlePanel,BorderLayout.CENTER);


        JButton seeMoreButton = new JButton("MORE");
        seeMoreButton.setFont(font30);
        seeMoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(seeMoreButtonClickedCount == 2) {
                    seeMoreButton.setVisible(false);
                    return ;
                }
                
                seeMoreButtonClickedCount++;
                number_News += 6;
                fullarticlePanel.setPreferredSize(new Dimension(1280, 2500 + 1000 * seeMoreButtonClickedCount));
                articlePanel.setPreferredSize(new Dimension(1280, 1500 + 1000 * seeMoreButtonClickedCount));
                articlePanel.setLayout(new GridLayout(6 + 3 * seeMoreButtonClickedCount,2,175,0));
                // createNews();
                addNews();
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
        // tao bang tin
        articlePanel.setVisible(true);
        createNews();
        addNews();
    }

    // tao bang tin
    public List<JPanel> createNews(){
        List<JsonObject> _JsonObjects = new GetData().getNewsElements();
        
        for(int i = 0; i < 30; i++){
            JPanel _JPanel = new newsObject(_JsonObjects.get(i)).setAsJPanel();
            newsList.add(_JPanel);
        }

        // for(int i = 0; i < number_News; i++){
        //     articlePanel.add(newsList.get(i));
        // }
        
        return newsList;
    }

    public void addNews(){
        for(int i = 0; i < number_News; i++){
            articlePanel.add(newsList.get(i));
        }
    }

    public void addBackButton() {
        menu.addBackButtonForMenu();
    }
}


