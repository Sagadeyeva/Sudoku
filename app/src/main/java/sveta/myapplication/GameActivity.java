package sveta.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity {

    private final static String TAG = "LAMA_SUDOKU";


    private Grid grid;
    private Grid sourceGrid;
    private TableLayout table;

    private int selectedX = -1;
    private int selectedY = -1;
    private TextView selectedCell = null;
    private Drawable selectedCellColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_game);
            grid = Grid.generate(40);
            sourceGrid=new Grid(grid);

            Log.i(TAG, "We are here");


            table = (TableLayout) findViewById(R.id.game_layout);

            table.setShrinkAllColumns(true);
            table.setStretchAllColumns(true);


            for (int j = 0; j < 9; j++) {
                TableRow row = new TableRow(this);
                for (int i = 0; i < 9; i++) {
                    final TextView text = new TextView(this);
                    int backgroundColor = 0;

                    if (((i < 3) || (i > 5)) && ((j < 3) || (j > 5))) {
                        backgroundColor = R.drawable.angle_cell_background;
                    } else if (((i > 2) && (i < 6)) && ((j > 2) && (j < 6))) {
                        backgroundColor = R.drawable.center_cell_background;
                    } else if (((i > 2) && (i < 6)) && ((j < 3) || (j > 5))) {
                        backgroundColor = R.drawable.vertical_cell_background;
                    } else if (((j > 2) && (j < 6)) && ((i < 3) || (i > 5))) {
                        backgroundColor = R.drawable.horizontal_cell_background;
                    }


                    text.setBackgroundResource(backgroundColor);
                    text.setTextColor(Color.BLACK);
                    text.setWidth(100);
                    text.setTextSize(35);
                    text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    int element = grid.getElement(i, j);
                    text.setText(element == 0 ? " " : String.valueOf(element));
                    final int ii = i;
                    final int jj = j;
                    //если ячейка занята, то возвращаем true
                    final boolean isOccupied = (grid.getElement(i, j) != 0);
                    //обработчик нажатия на выбранную пустую ячейку для дальнейшего заполнения
                    text.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!isOccupied) {
                                selectedX = ii;
                                selectedY = jj;
                                //проеверяем, если текущий элемент (text) невыбранный, чтобы при нажатии двойном не
                                // сбросился изначальный фоновый цвет
                                if (selectedCell != text) {
                                    if (selectedCell != null)
                                        selectedCell.setBackground(selectedCellColor);
                                    selectedCell = text;
                                    selectedCellColor = text.getBackground();
                                    text.setBackgroundColor(Color.RED);
                                }


                            }
                        }
                    });
                    row.addView(text);
                }

                table.addView(row);
            }
//кнопки для вставления чиесл польхователем в сетку
            TableRow firstButtonRow = new TableRow(this);
            TableRow secondButtonRow = new TableRow(this);
            TableRow thirdButtonRow = new TableRow(this);

            for (int i = 0; i < 10; i++) {
                Button number = new Button(this);
                number.setTextSize(30);
                number.setWidth(90);
                if (i == 0) {
                    number.setText(" ");
                } else {
                    number.setText(String.valueOf(i));
                }

                final int value = i;

                number.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (selectedY != -1 && selectedX != -1) {
                            grid.setElement(selectedY, selectedX, value);
                            //  TextView text = selectedCell;
                            TableRow row = (TableRow) table.getChildAt(selectedY);
                            TextView text = (TextView) row.getChildAt(selectedX);
                            text.setBackgroundResource(R.drawable.changed_cell_background);
                            selectedCellColor = getDrawable(R.drawable.changed_cell_background);
                            text.setText(value == 0 ? " " : String.valueOf(value));
                        }
                    }
                });

                if (i < 5) {
                    firstButtonRow.addView(number);
                } else {
                    secondButtonRow.addView(number);
                }

            }

            Button retry=new Button(this);
            retry.setText("<-");
            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        grid = new Grid(sourceGrid);
                        for (int j = 0; j < 9; j++) {
                            TableRow row = (TableRow) table.getChildAt(j);
                            for (int i = 0; i < 9; i++) {
                                TextView text = (TextView) row.getChildAt(i);
                                text.setText(String.valueOf(grid.getElement(i, j)));
                                int elem=grid.getElement(i, j);
                                text.setText(elem == 0 ? " " : String.valueOf(elem));
                            }
                        }
                    } catch (Exception e) {
                        Log.i(TAG, e.toString());
                    }
                }
            });


            Button check = new Button(this);
            check.setText("OK");

            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (grid.isCorrectlySolved()) {

                            Toast message = Toast.makeText(getApplicationContext(),
                                    "Congratulations! Solved correctly! :)",
                                    Toast.LENGTH_SHORT);
                            message.setGravity(Gravity.CENTER, 0, 0);
                            message.show();
                        } else {

                            Toast message = Toast.makeText(getApplicationContext(),
                                    "Try again :(",
                                    Toast.LENGTH_SHORT);
                            message.setGravity(Gravity.CENTER, 0, 0);
                            message.show();
                        }
                    } catch (Exception e) {
                        Log.i(TAG, e.toString());
                    }
                }

            });

            Button solve = new Button(this);
            solve.setText("?");
            solve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        Algorithm solver = new Algorithm();
                        grid = solver.solve(grid);
                        for (int j = 0; j < 9; j++) {
                            TableRow row = (TableRow) table.getChildAt(j);
                            for (int i = 0; i < 9; i++) {
                                TextView text = (TextView) row.getChildAt(i);
                                text.setText(String.valueOf(grid.getElement(i, j)));
                                //text.setBackgroundResource(R.drawable.changed_cell_background);
                            }
                        }
                    } catch (Exception e) {
                        Log.i(TAG, e.toString());
                    }
                }
            });


            secondButtonRow.addView(solve);
            firstButtonRow.addView(check);
            firstButtonRow.addView(retry);

            TableRow emptyRow = new TableRow(this);
            emptyRow.setMinimumHeight(40);
            table.addView(emptyRow);

            table.addView(firstButtonRow);
            table.addView(secondButtonRow);


        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
    }


}
