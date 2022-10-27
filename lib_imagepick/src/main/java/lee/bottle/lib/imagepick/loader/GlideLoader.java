package lee.bottle.lib.imagepick.loader;

import android.content.Context;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;

import java.lang.ref.SoftReference;

import lee.bottle.lib.imagepick.R;
import lee.bottle.lib.imagepick.utils.ImageLoader;


/**
 * Created by Leeping on 2019/6/4.
 * email: 793065165@qq.com
 */
public class GlideLoader implements ImageLoader {

    private RequestOptions mOptions = new RequestOptions()
            .centerCrop()
            .dontAnimate()
            .format(DecodeFormat.PREFER_RGB_565)
            .placeholder(R.mipmap.icon_image_default)
            .error(R.mipmap.icon_image_default);

    private RequestOptions mPreOptions = new RequestOptions()
            .skipMemoryCache(true)
            .error(R.mipmap.icon_image_default);

    private final SoftReference<Context> contextRef;

    public GlideLoader(Context appContext) {
        this.contextRef = new SoftReference<>(appContext);
    }

    @Override
    public void loadImage(ImageView imageView, String imagePath) {
        //小图加载
        Glide.with(imageView.getContext()).load(imagePath).apply(mOptions).into(imageView);
    }

    @Override
    public void loadPreImage(ImageView imageView, String imagePath) {
        //大图加载
        Glide.with(imageView.getContext()).load(imagePath).apply(mPreOptions).into(imageView);

    }

    @Override
    public void clearMemoryCache() {
        //清理缓存
        Glide.get(contextRef.get()).clearMemory();
    }
}
