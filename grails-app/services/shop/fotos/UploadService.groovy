package shop.fotos

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


    public static final String ORIG_DIR_NAME = 'uploads'
    public static final String THUMBS_DIR_NAME = 'thumbnails'

    AssetResourceLocator assetResourceLocator


    List<Foto> uploadFotos( final FotoUploadCommand cmd ) {

        // TODO image-hashcode ausrechnen und als unique constraint prop in Domain class Foto aufnehmen?

        final List<Tag> tags = cmd.tags?.collect {tag -> Tag.findOrCreateWhere( name: tag ) } ?: Collections.emptyList()

        final Watermark watermark = new Watermark(
                Positions.CENTER,
                ImageIO.read( assetResourceLocator?.findResourceForURI( 'watermark.png' )?.inputStream ),
                0.5f )

        final List<Foto> uploadedFotos = []
        long tookTN = 0
        for( MultipartFile file : cmd.files ) {
            final Path filePath = Paths.get( ORIG_DIR_NAME, file.originalFilename )
            try( OutputStream os = Files.newOutputStream( filePath ) ) {
                // save original image file to disk
                os.write( file.bytes )

                // create thumbnail
                final String thumbnailFilename = Rename.PREFIX_DOT_THUMBNAIL.apply( file.originalFilename, null )
                final Path thumbnailPath = Paths.get( THUMBS_DIR_NAME, thumbnailFilename )
                final File origFile = new File( filePath.toString() )
                long startTN = System.currentTimeMillis()
                Thumbnails.of( origFile )
                        .size( 480, 320 )
                        .outputFormat( 'jpg' )
                        .watermark( watermark )
                        .outputQuality( 0.8 )
                        .asFiles( [new File( thumbnailPath.toString() )] )
                tookTN += System.currentTimeMillis() - startTN

                log.info( "Successfully saved '${ filePath }' to disk. Will create a thumbnail at '${thumbnailPath}'")

                // create Foto
                final Foto foto = new Foto(
                        origFilename: file.originalFilename,
                        thumbnail: thumbnailFilename
                )
                // with Tags
                foto.tagsToAdd.addAll( tags )

                uploadedFotos << foto
            }
        }
        println "Thumbnail creation took ${tookTN} ms."

        uploadedFotos
    }
 }
