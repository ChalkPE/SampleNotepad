package pe.chalk.dimigo.notepad;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author ChalkPE <chalkpe@gmail.com>
 * @since 2016-05-06
 */
public class SampleNotepad extends JFrame {
    public static void main(String[] args){
        new SampleNotepad().setVisible(true);
    }

    private JButton openButton;
    private JTextArea textArea;
    private Path openPath;

    private SampleNotepad(){
        this.setSize(400, 600); this.setTitle("Notepad");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        this.openButton = new JButton("Open");
        this.openButton.addActionListener(this::chooseFile);
        this.getContentPane().add(this.openButton, BorderLayout.NORTH);

        this.textArea = new JTextArea();
        this.textArea.setMargin(new Insets(3, 5, 3, 5));
        final JScrollPane textScroll = new JScrollPane(this.textArea);
        this.getContentPane().add(textScroll, BorderLayout.CENTER);

        final JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this::saveFile);
        this.getContentPane().add(saveButton, BorderLayout.SOUTH);
    }

    private void chooseFile(final ActionEvent event){
        final JFileChooser chooser = new JFileChooser();
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) try{
            this.openPath = chooser.getSelectedFile().toPath();
            this.openButton.setText(this.openPath.getFileName().toString());
            this.textArea.setText(String.join(System.lineSeparator(), Files.readAllLines(this.openPath, StandardCharsets.UTF_8)));
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private void saveFile(final ActionEvent event){
        try{
            if(this.openPath != null) Files.write(this.openPath, this.textArea.getText().getBytes(StandardCharsets.UTF_8));
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
