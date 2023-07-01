package com.example.mortallabyrinth;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.MotionEvent;
import android.view.View;
import android.media.MediaPlayer;
import android.content.Intent;

public class MazeView extends View {
    private Bitmap endGameBitmap;
    private Paint wallPaint;
    private Paint playerPaint;
    private Bitmap backgroundBitmap;
    private Bitmap wallBitmap;
    private int playerX = 1;  // La position initiale du joueur
    private int playerY = 1;
    private MediaPlayer mediaPlayer;

    private int[][] maze = {
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0},
            {0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0},
            {0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0},
            {0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0},
            {0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0},
            {0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0},
            {1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0},
            {1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0},
            {0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0},
            {0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0},
            {0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0},
            {1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 3, 3, 3},
            {0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 3, 4, 3},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3}
    };

    private Bitmap playerBitmap;
    private Bitmap scaledPlayerBitmap;
    private Bitmap winBitmap;
    private Paint gravelPaint;
    private Bitmap gravelBitmap;
    public MazeView(Context context) {
        super(context);
        endGameBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.win);
        wallPaint = new Paint();
        wallBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lave);
        wallPaint.setShader(new BitmapShader(wallBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        playerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.personnage);
        scaledPlayerBitmap = Bitmap.createScaledBitmap(playerBitmap, 80, 80, true);
        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gravier);
        mediaPlayer = MediaPlayer.create(context, R.raw.music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        gravelBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gravier);
        gravelPaint = new Paint();
        gravelPaint.setShader(new BitmapShader(gravelBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        // Replace the current line where you decode the bitmap with the following
        Bitmap originalWinBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.win);

// Now create a scaled bitmap. You can replace the numbers 200, 200 with the desired size.
        winBitmap = Bitmap.createScaledBitmap(originalWinBitmap, 500, 500, true);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        RectF dst = new RectF(0, 0, getWidth(), getHeight());
        canvas.drawBitmap(backgroundBitmap, null, dst, null);

        float cellWidth = (float) getWidth() / maze[0].length;
        float cellHeight = (float) getHeight() / maze.length;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 0 || maze[i][j] == 3) {  // Ajout du dessin de mur aussi quand maze[i][j] est égal à 3
                    canvas.drawRect(
                            j * cellWidth,
                            i * cellHeight,
                            (j + 1) * cellWidth,
                            (i + 1) * cellHeight,
                            wallPaint
                    );
                }
                if (maze[i][j] == 3) {  // On dessine le gravier quand maze[i][j] est égal à 3
                    canvas.drawRect(
                            j * cellWidth,
                            i * cellHeight,
                            (j + 1) * cellWidth,
                            (i + 1) * cellHeight,
                            gravelPaint
                    );
                }
                if (maze[i][j] == 4) {
                    // On dessine l'image de victoire par-dessus le mur
                    RectF winDst = new RectF(j * cellWidth, i * cellHeight, (j + 1) * cellWidth, (i + 1) * cellHeight);
                    canvas.drawBitmap(winBitmap, null, winDst, null);
                }

            }
        }

        float playerHalfWidth = scaledPlayerBitmap.getWidth() / 2.0f;
        float playerHalfHeight = scaledPlayerBitmap.getHeight() / 2.0f;
        canvas.drawBitmap(scaledPlayerBitmap,
                (playerX + 0.5f) * cellWidth - playerHalfWidth,
                (playerY + 0.5f) * cellHeight - playerHalfHeight,
                null);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) (event.getX() / getWidth() * maze[0].length);
            int y = (int) (event.getY() / getHeight() * maze.length);

            if (playerX != x && playerY != y) {
                return true;
            }

            if (playerX == x) {
                int min = Math.min(playerY, y);
                int max = Math.max(playerY, y);
                for (int i = min; i <= max; i++) {
                    if (maze[i][x] == 0) {
                        return true;
                    }
                }
            } else {
                int min = Math.min(playerX, x);
                int max = Math.max(playerX, x);
                for (int i = min; i <= max; i++) {
                    if (maze[y][i] == 0) {
                        return true;
                    }
                }
            }

            playerX = x;
            playerY = y;
            if (maze[playerY][playerX] == 3) {
                Intent intent = new Intent(getContext(), WinActivity.class);
                getContext().startActivity(intent);
            }

            invalidate();

            return true;
        }

        return false;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
