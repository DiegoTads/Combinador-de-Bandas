/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package combinador;

import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.awt.image.renderable.ParameterBlock;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.TiledImage;

/**
 *
 * @author Diego
 */
public class Combinador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        String[][] arquivos = new String[][] {
            {"432","Imagens\\LO82210722017045CUB00_B4.tif","Imagens\\LO82210722017045CUB00_B3.tif","Imagens\\LO82210722017045CUB00_B2.tif"},
            {"764","Imagens\\LO82210722017045CUB00_B7.tif","Imagens\\LO82210722017045CUB00_B6.tif","Imagens\\LO82210722017045CUB00_B4.tif"},
            {"543","Imagens\\LO82210722017045CUB00_B5.tif","Imagens\\LO82210722017045CUB00_B4.tif","Imagens\\LO82210722017045CUB00_B3.tif"},
            {"652","Imagens\\LO82210722017045CUB00_B6.tif","Imagens\\LO82210722017045CUB00_B5.tif","Imagens\\LO82210722017045CUB00_B2.tif"},
            {"765","Imagens\\LO82210722017045CUB00_B7.tif","Imagens\\LO82210722017045CUB00_B6.tif","Imagens\\LO82210722017045CUB00_B5.tif"},
            {"562","Imagens\\LO82210722017045CUB00_B5.tif","Imagens\\LO82210722017045CUB00_B6.tif","Imagens\\LO82210722017045CUB00_B2.tif"},
            {"564","Imagens\\LO82210722017045CUB00_B5.tif","Imagens\\LO82210722017045CUB00_B6.tif","Imagens\\LO82210722017045CUB00_B4.tif"},
            {"753","Imagens\\LO82210722017045CUB00_B7.tif","Imagens\\LO82210722017045CUB00_B5.tif","Imagens\\LO82210722017045CUB00_B3.tif"},
            {"754","Imagens\\LO82210722017045CUB00_B7.tif","Imagens\\LO82210722017045CUB00_B5.tif","Imagens\\LO82210722017045CUB00_B4.tif"},
            {"654","Imagens\\LO82210722017045CUB00_B6.tif","Imagens\\LO82210722017045CUB00_B5.tif","Imagens\\LO82210722017045CUB00_B4.tif"},
        };
         
        int c=0;
        
        do
        {
            System.out.println("Imagem "+arquivos[c][0]+"...");
            
            PlanarImage imagemR = JAI.create("fileload", arquivos[c][1]);
            PlanarImage imagemG = JAI.create("fileload", arquivos[c][2]);
            PlanarImage imagemB = JAI.create("fileload", arquivos[c][3]);
            ParameterBlock pb = new ParameterBlock();
            pb.addSource(imagemR);
            pb.addSource(imagemG);
            pb.addSource(imagemB);

            PlanarImage imagemResultado = JAI.create("bandmerge",pb);
            int largura = imagemResultado.getWidth();
            int altura = imagemResultado.getHeight();
            SampleModel sm = imagemResultado.getSampleModel();
            int nbandas = sm.getNumBands();
            Raster rasterIR = imagemResultado.getData();
            WritableRaster rasterEscrita1 = rasterIR.createCompatibleWritableRaster();
            float[] pixelsIR = new float[largura*altura*nbandas];
            rasterIR.getPixels(0, 0, largura, altura, pixelsIR);
            int deslocamento=0;

            // Atribui a cada pixel de cada camada o valor do pixel mutiplicado por uma grandeza para aumentar seu brilho ou contraste
            for (int a=0;a<altura;a++) 
            {
                for (int l=0;l<largura;l++) 
                {    
                    for(int i=0;i<nbandas;i++)
                    {
                        pixelsIR[deslocamento]=(float) (pixelsIR[deslocamento]*(1.5));

                        deslocamento++;
                    }
                }
            }

            // Grava a imagem RGB
            rasterEscrita1.setPixels(0, 0, largura, altura, pixelsIR);
            TiledImage ti1 = new TiledImage(imagemResultado,imagemResultado.getTileWidth(),imagemResultado.getTileHeight());
            ti1.setData(rasterEscrita1);
            JAI.create("filestore",ti1,"ImagensR\\"+arquivos[c][0]+".tif","TIFF");
            
            System.out.println("Imagem "+arquivos[c][0]+"...OK\n");
            
            c++;
           
        }while(c<10);
    }    
}
