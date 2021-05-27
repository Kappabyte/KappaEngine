package net.kappabyte.kappaengine.graphics;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;

import org.joml.Vector2i;
import org.lwjgl.system.MemoryUtil;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
import net.kappabyte.kappaengine.util.Log;

public class TextureAtlas {

    public Texture texture;

    private HashMap<String, AtlasTexture> textures = new HashMap<>();

    public TextureAtlas() {

    }

    public TextureAtlas setTextures(NamedAsset... textureAssets) {
        PNGDecoder decoder;

        int atlasSize = (int) Math.ceil(Math.sqrt(textureAssets.length));

        int totalWidth = 0;
        int totalHeight = 0;

        textures.clear();

        for(int i = 0; i < textureAssets.length; i++) {
            int atlasX = i % atlasSize;
            int atlasY = i / atlasSize;
            try {
                decoder = new PNGDecoder(TextureAtlas.class.getResourceAsStream("/" + textureAssets[i].assetURL));

                ByteBuffer textureData = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
                decoder.decode(textureData, 0, Format.RGBA);
                textureData.flip();
                textures.put(textureAssets[i].assetName, new AtlasTexture(textureData, new Vector2i(atlasX * decoder.getWidth(), atlasY * decoder.getHeight()), new Vector2i(atlasX * decoder.getWidth() + decoder.getWidth(), atlasY * decoder.getHeight() + decoder.getHeight()), textureAssets[i].assetName));

                totalWidth += decoder.getWidth();
                totalHeight += decoder.getHeight();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ByteBuffer atlas = ByteBuffer.allocateDirect(4 * totalWidth * totalHeight);
        int offset = 0;
        for(AtlasTexture texture : textures.values()) {
            atlas.put(texture.data);
            offset += texture.data.capacity();
        }
        while(atlas.hasRemaining()) {
            atlas.put((byte) 0);
        }
        atlas.flip();
        Log.debug("Generating texture...");
        AtlasTexture test = textures.get("grass_block_side");
        texture = new Texture(atlas, totalWidth, totalHeight);

        return this;
    }

    public Texture getTexture() {
        return texture;
    }

    public static class AtlasTexture {
        Vector2i start, end;
        String name;
        ByteBuffer data;

        public AtlasTexture(ByteBuffer data, Vector2i start, Vector2i end, String name) {
            this.data = data;
            this.start = start;
            this.end = end;
            this.name = name;
        }
    }
}
