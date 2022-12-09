package cm;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Christoph Percival
 */
public class Image {
    
    public ImageIcon resizeImage(String imgPath, int width, int height)
    {
        ImageIcon imgIcon = new ImageIcon(imgPath);
        java.awt.Image img = imgIcon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img);
        return image;
    }
    
    public ImageIcon resizeImage(BufferedImage bufImage, int width, int height)
    {
        ImageIcon imgIcon = new ImageIcon(bufImage);
        java.awt.Image img = imgIcon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img);
        return image;
    }
    
    public String browse(JLabel label)
    {
        String pathLocal = null;
        // Load Image
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        
        //File Extension
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("*.Images", "jpg", "png", "gif");
        fileChooser.addChoosableFileFilter(fileFilter);
        
        int fileState = fileChooser.showSaveDialog(null);
        
        //Check If User Selected A Value
        if (fileState == JFileChooser.APPROVE_OPTION){
            File selectedFile =  fileChooser.getSelectedFile();
            pathLocal = selectedFile.getAbsolutePath();
            
            label.setIcon(resizeImage(pathLocal, label.getWidth(), label.getHeight()));
        }
        else
            if (fileState == JFileChooser.CANCEL_OPTION)
            {
                System.out.println("Image Not Selected");
            }
        
        return pathLocal;
    }
    
}
