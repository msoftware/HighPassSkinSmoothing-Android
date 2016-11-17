#ifndef _MAGIC_BEAUTY_H_
#define _MAGIC_BEAUTY_H_

#include "bitmap/JniBitmap.h"

class AmniXSkinBeauty {
public:
    void initBeauty(JniBitmap *jniBitmap);

    void unInitBeauty();

    void startSkinSmooth(float smoothlevel);

    void startWhiteSkin(float whitenlevel);

    static AmniXSkinBeauty *getInstance();

    ~AmniXSkinBeauty();

    void _startBeauty(float smoothlevel, float whitenlevel);

private:
    static AmniXSkinBeauty *instance;

    AmniXSkinBeauty();

    uint64_t *mIntegralMatrix;
    uint64_t *mIntegralMatrixSqr;

    uint32_t *storedBitmapPixels;
    uint32_t *mImageData_rgb;

    uint8_t *mImageData_yuv;
    uint8_t *mSkinMatrix;

    int mImageWidth;
    int mImageHeight;
    float mSmoothLevel;
    float mWhitenLevel;

    void initIntegral();

    void initSkinMatrix();

    void _startSkinSmooth(float smoothlevel);

    void _startWhiteSkin(float whitenlevel);
};

#endif
