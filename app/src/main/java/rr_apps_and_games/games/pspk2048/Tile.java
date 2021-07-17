package rr_apps_and_games.games.pspk2048;

import android.widget.ImageButton;


class Tile {

    int value;
    ImageButton mImageButton;
    public Tile(int val, int row, int col, MainActivity activity) {
        value = val;

        if(row==0&&col==0){
            mImageButton = (ImageButton)activity.findViewById(R.id.button_0_0);
        }
        else if(row==0&&col==1){
            mImageButton = (ImageButton)activity.findViewById(R.id.button_0_1);
        }
        else if(row==1&&col==0){
            mImageButton = (ImageButton)activity.findViewById(R.id.button_1_0);
        }
        else if(row==0&&col==2){
            mImageButton = (ImageButton)activity.findViewById(R.id.button_0_2);
        }
        else if(row==0&&col==3){
            mImageButton = (ImageButton)activity.findViewById(R.id.button_0_3);
        }
        else if(row==1&&col==1){
            mImageButton = (ImageButton)activity.findViewById(R.id.button_1_1);
        }
        else if(row==1&&col==2){
            mImageButton = (ImageButton)activity.findViewById(R.id.button_1_2);
        }
        else if(row==1&&col==3){
            mImageButton = (ImageButton)activity.findViewById(R.id.button_1_3);
        }
        else if(row==2&&col==0){
            mImageButton = (ImageButton)activity.findViewById(R.id.button_2_0);
        }
        else if(row==2&&col==1){
            mImageButton = (ImageButton)activity.findViewById(R.id.button_2_1);
        }
        else if(row==2&&col==2){
            mImageButton = (ImageButton)activity.findViewById(R.id.button_2_2);
        }
        else if(row==2&&col==3){
            mImageButton = (ImageButton)activity.findViewById(R.id.button_2_3);
        }
        else if(row==3&&col==0){
            mImageButton = (ImageButton)activity.findViewById(R.id.button_3_0);
        }
        else if(row==3&&col==1){
            mImageButton = (ImageButton)activity.findViewById(R.id.button_3_1);
        }
        else if(row==3&&col==2){
            mImageButton = (ImageButton)activity.findViewById(R.id.button_3_2);
        }
        else if(row==3&&col==3){
            mImageButton = (ImageButton)activity.findViewById(R.id.button_3_3);
        }


        switch(value){
            case 2:
                mImageButton.setImageResource(R.drawable.tholiprema_withnumber);
                break;
            case 4:
                mImageButton.setImageResource(R.drawable.thammudu_withnumber);
                break;
            case 8:
                mImageButton.setImageResource(R.drawable.badri_withnumber);
                break;
            case 16:
                mImageButton.setImageResource(R.drawable.kushi_withnumber);
                break;
            case 32:
                mImageButton.setImageResource(R.drawable.balu_withnumber);
                break;
            case 64:
                mImageButton.setImageResource(R.drawable.jalsa_withnumber);
                break;
            case 128:
                mImageButton.setImageResource(R.drawable.theenmaar_withnumber);
                break;
            case 256:
                mImageButton.setImageResource(R.drawable.panjaa_withnumber);
                break;
            case 512:
                mImageButton.setImageResource(R.drawable.gabbarsingh_withnumber);
                break;
            case 1024:
                mImageButton.setImageResource(R.drawable.gopalagopala_withnumber);
                break;
            case 2048:
                mImageButton.setImageResource(R.drawable.janasena_withnumber);
                break;
            case 4096:
                mImageButton.setImageResource(R.drawable.vakeelsaab_withnumber);
                break;
            case 8192:
                mImageButton.setImageResource(R.drawable.pspk27_withnumber);
                break;
            case 16384:
                mImageButton.setImageResource(R.drawable.pk_16384);
                break;
            case 32768:
                mImageButton.setImageResource(R.drawable.pk_32768);
                break;
            case 65536:
                mImageButton.setImageResource(R.drawable.pk_65536);
                break;
            case 131072:
                mImageButton.setImageResource(R.drawable.pk_131072);
                break;
        }
    }

    public void modify(int value){
        this.value = value;
        //mImageButton.setImageResource(getImgResc(value));

        switch(value){
            case 0:
                mImageButton.setImageResource(0);
                break;
            case 2:
                mImageButton.setImageResource(R.drawable.tholiprema_withnumber);
                break;
            case 4:
                mImageButton.setImageResource(R.drawable.thammudu_withnumber);
                break;
            case 8:
                mImageButton.setImageResource(R.drawable.badri_withnumber);
                break;
            case 16:
                mImageButton.setImageResource(R.drawable.kushi_withnumber);
                break;
            case 32:
                mImageButton.setImageResource(R.drawable.balu_withnumber);
                break;
            case 64:
                mImageButton.setImageResource(R.drawable.jalsa_withnumber);
                break;
            case 128:
                mImageButton.setImageResource(R.drawable.theenmaar_withnumber);
                break;
            case 256:
                mImageButton.setImageResource(R.drawable.panjaa_withnumber);
                break;
            case 512:
                mImageButton.setImageResource(R.drawable.gabbarsingh_withnumber);
                break;
            case 1024:
                mImageButton.setImageResource(R.drawable.gopalagopala_withnumber);
                break;
            case 2048:
                mImageButton.setImageResource(R.drawable.janasena_withnumber);
                break;
            case 4096:
                mImageButton.setImageResource(R.drawable.vakeelsaab_withnumber);
                break;
            case 8192:
                mImageButton.setImageResource(R.drawable.pspk27_withnumber);
                break;
            case 16384:
                mImageButton.setImageResource(R.drawable.pk_16384);
                break;
            case 32768:
                mImageButton.setImageResource(R.drawable.pk_32768);
                break;
            case 65536:
                mImageButton.setImageResource(R.drawable.pk_65536);
                break;
            case 131072:
                mImageButton.setImageResource(R.drawable.pk_131072);
                break;
        }

    }


}