package ea.fotos

import asset.pipeline.grails.AssetResourceLocator
import grails.gorm.transactions.Transactional
import net.coobird.thumbnailator.filters.Watermark
import net.coobird.thumbnailator.geometry.Positions
import net.coobird.thumbnailator.name.Rename
import net.coobird.thumbnailator.Thumbnails
import org.springframework.web.multipart.MultipartFile

import javax.imageio.ImageIO
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Transactional
class UploadService {


    final static String ORG_DIR_NAME = 'uploads'
    final static String THUMBS_DIR_NAME = 'thumbnails'

    AssetResourceLocator assetResourceLocator


    List<Foto> uploadFotos( final FotoUploadCommand cmd ) {

        // TODO image-hashcode ausrechnen und als unique constraint prop in Domain class Foto aufnehmen?

        final Watermark watermark = new Watermark(
                Positions.CENTER,
                ImageIO.read( assetResourceLocator?.findResourceForURI( 'watermark.png' )?.inputStream ),
                0.5f )

        final List<Foto> uploadedFotos = []
        for( MultipartFile file : cmd.files ) {
            final Path filePath = Paths.get( ORG_DIR_NAME, file.originalFilename )
            try( OutputStream os = Files.newOutputStream( filePath ) ) {
                // save original image file to disk
                os.write( file.bytes )

                // create thumbnail
                final Path thumbnailPath = Paths.get( THUMBS_DIR_NAME, Rename.PREFIX_DOT_THUMBNAIL.apply( file.originalFilename, null ) )
                final File orgFile = new File( filePath.toString() )
                Thumbnails.of( orgFile )
                        .size( 640, 480 )
                        .watermark( watermark )
                        .outputQuality( 0.8 )
                        .asFiles( [new File( thumbnailPath.toString() )] )

                log.info( "Successfully saved '${ filePath }' to disk. Will create a thumbnail at '${thumbnailPath}'")

                // create Foto
                uploadedFotos << new Foto(
                        orgFilename: file.originalFilename,
                        thumbnail: thumbnailPath.toString()
                )
            }
        }

        uploadedFotos
    }
 }
