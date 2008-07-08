/**
 * Copyright 2007 DFKI GmbH.
 * All Rights Reserved.  Use is subject to license terms.
 * 
 * Permission is hereby granted, free of charge, to use and distribute
 * this software and its documentation without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of this work, and to
 * permit persons to whom this work is furnished to do so, subject to
 * the following conditions:
 * 
 * 1. The code must retain the above copyright notice, this list of
 *    conditions and the following disclaimer.
 * 2. Any modifications must be clearly marked as such.
 * 3. Original authors' names are not deleted.
 * 4. The authors' names are not used to endorse or promote products
 *    derived from this software without specific prior written
 *    permission.
 *
 * DFKI GMBH AND THE CONTRIBUTORS TO THIS WORK DISCLAIM ALL WARRANTIES WITH
 * REGARD TO THIS SOFTWARE, INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS, IN NO EVENT SHALL DFKI GMBH NOR THE
 * CONTRIBUTORS BE LIABLE FOR ANY SPECIAL, INDIRECT OR CONSEQUENTIAL
 * DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR
 * PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS
 * ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF
 * THIS SOFTWARE.
 */
package marytts.tools.voiceimport;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

import javax.swing.*;

public class HelpGUI {
    private final JEditorPane editPane;
    
    public HelpGUI(InputStream fileIn)
    {
        editPane = new JEditorPane();        
        editPane.setContentType("text/html; charset=UTF-8");        
        try{
            editPane.read(new InputStreamReader(fileIn, "UTF-8"), null);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Could not read file : "
                    +e.getMessage());            
        }
        editPane.setPreferredSize(new Dimension(700, 500));
        editPane.setEditable(false);
    }
    
    public HelpGUI(String text){
        editPane = new JEditorPane();
        editPane.setPreferredSize(new Dimension(700, 500));
        editPane.setContentType("text/html; charset=UTF-8");   
        editPane.setText(text);
        editPane.setEditable(false);
    }
    
    /**
     * Show a frame displaying the help file.
     * @param file the help file 
     * @return true, if no error occurred
     */
    public boolean display()
    {
        final JFrame frame = new JFrame("Help");
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gridC = new GridBagConstraints();
        frame.getContentPane().setLayout( gridBagLayout );

        gridC.gridx = 0;
        gridC.gridy = 0;
        // resize scroll pane:
        gridC.weightx = 1;
        gridC.weighty = 1;
        gridC.fill = GridBagConstraints.HORIZONTAL;
        JScrollPane scrollPane = new JScrollPane(editPane);
        scrollPane.setPreferredSize(editPane.getPreferredSize());
        gridBagLayout.setConstraints( scrollPane, gridC );
        frame.getContentPane().add(scrollPane);
        gridC.gridy = 1;
        // do not resize buttons:
        gridC.weightx = 0;
        gridC.weighty = 0;
        JButton exitButton = new JButton("Quit");
        exitButton.setMnemonic(KeyEvent.VK_Q);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(exitButton);
        gridBagLayout.setConstraints( buttonPanel, gridC );
        frame.getContentPane().add(buttonPanel);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                frame.setVisible(false);
            }
        });
        frame.pack();
        frame.setVisible(true);
        do {
            try {
                Thread.sleep(10); // OK, this is ugly, but I don't mind today...
            } catch (InterruptedException e) {
                return false;
            }
        } while (frame.isVisible());
        
        frame.dispose(); 
        return true;
    }
    
    
}