package sveta.myapplication;

import android.app.Activity;
import android.content.pm.ActivityInfo;
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
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity {

    private final static String TAG = "LAMA_SUDOKU";


    private Grid grid;
    private Grid sourceGrid;
    private TableLayout table;
    private GridLayout field;

    private int selectedX = -1;
    private int selectedY = -1;
    private TextView selectedCell = null;
    private Drawable selectedCellColor;
    private Drawable selectedCellDefaultColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        try {
            setContentView(R.layout.activity_game);
            grid = Grid.generate(40);
            sourceGrid = new Grid(grid);

            Log.i(TAG, "We are here");


            table = (TableLayout) findViewById(R.id.game_layout);
            field = (GridLayout) findViewById(R.id.field);


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
                    final Drawable defaultColor = getDrawable(backgroundColor);
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
                                    selectedCellDefaultColor = defaultColor;
                                    text.setBackgroundColor(Color.RED);
                                }else {
                                    text.setBackgroundColor(Color.RED);
                                }


                            }
                        }
                    });
                    row.addView(text);
                }

                table.addView(row);
            }
//кнопки для вставления чиесл пользователем в сетку
            //TableRow firstButtonRow = new TableRow(this);
            //TableRow secondButtonRow = new TableRow(this);


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
                            if(value!=0)
                            if (!(grid.checkLine(selectedX, value) &&
                                    grid.checkColumn(selectedY, value) &&
                                    grid.checkSquare(selectedX, selectedY, value))) {
                                Toast message = Toast.makeText(getApplicationContext(),
                                        "Think more properly!",
                                        Toast.LENGTH_SHORT);
                                message.setGravity(Gravity.CENTER, 0, 0);
                                message.show();
                                return;
                            }
                            grid.setElement(selectedX, selectedY, value);
                            //  TextView text = selectedCell;
                            TableRow row = (TableRow) table.getChildAt(selectedY);
                            TextView text = (TextView) row.getChildAt(selectedX);
                            if (value != 0) {
                                text.setBackgroundResource(R.drawable.changed_cell_background);
                                selectedCellColor = getDrawable(R.drawable.changed_cell_background);
                            } else {
                                text.setBackground(selectedCellDefaultColor);
                                selectedCellColor = selectedCellDefaultColor;
                            }
                            text.setText(value == 0 ? " " : String.valueOf(value));
                        }
                    }
                });

//                if (i < 5) {
//                    firstButtonRow.addView(number);
//                } else {
//                    secondButtonRow.addView(number);
//                }
                field.addView(number);
            }

            Button retry = new Button(this);
            retry.setText("clear");
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
                                int elem = grid.getElement(i, j);
                                text.setText(elem == 0 ? " " : String.valueOf(elem));
                            }
                        }
                    } catch (Exception e) {
                        Log.i(TAG, e.toString());
                    }
                }
            });


            Button check = new Button(this);
            check.setText("       check me :)    ");
            check.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

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
            solve.setText("help!");
            solve.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
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


//            secondButtonRow.addView(solve);
//            firstButtonRow.addView(check);
//            firstButtonRow.addView(retry);
            GridLayout.Spec columnSpec = GridLayout.spec(1, 3);
            GridLayout.Spec rowSpec = GridLayout.spec(3, 1);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);

            field.addView(solve);
            field.addView(check, params);

            columnSpec = GridLayout.spec(4, 1);
            params = new GridLayout.LayoutParams(rowSpec, columnSpec);

            field.addView(retry, params);


        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
    }


}
