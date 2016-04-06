package sveta.myapplication;

import android.os.Binder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private Grid grid;
    private TableLayout table;

    private int selectedX = -1;
    private int selectedY = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        GridLayout layout = new GridLayout(this);

        grid = Grid.generate(0);

        table = new TableLayout(this);
        table.setShrinkAllColumns(true);
        table.setStretchAllColumns(true);
        for (int j = 0; j < 9; j++) {
            TableRow row = new TableRow(this);
            for (int i = 0; i < 9; i++) {
                TextView text = new TextView(this);
                text.setText(String.valueOf(grid.getElement(i, j)));
                row.addView(text);
            }
            table.addView(row);
        }

        table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < table.getChildCount(); i++) {
                    TableRow row = (TableRow) table.getChildAt(i);
                    for (int j = 0; j < row.getChildCount(); j++) {
                        View element = row.getChildAt(j);
                        if (element.isFocused()) {
                            selectedX = i;
                            selectedY = j;
                            return;
                        }
                    }
                }
            }
        });
        layout.addView(table, 0, 0);


        for (int i = 0; i < 10; i++) {
            Button number = new Button(this);

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
//TODO условие првоерки на занятость ячейки по заданию и насколько корректно значеие, которое вставляет пользователь
                        TableRow row = (TableRow) table.getChildAt(selectedY);
                        TextView text = (TextView) row.getChildAt(selectedX);
                        text.setText(value == 0 ? " " : String.valueOf(value));
                    }
                }
            });

            layout.addView(number, i < 5 ? 1 : 2, i % 5);

        }


        Button solve = new Button(this);
        solve.setText("solve");
        solve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Algorithm solver = new Algorithm();
                grid = solver.solve(grid);
                for (int j = 0; j < 9; j++) {
                    TableRow row = (TableRow) table.getChildAt(j);
                    for (int i = 0; i < 9; i++) {
                        TextView text = (TextView) row.getChildAt(i);
                        text.setText(String.valueOf(grid.getElement(i, j)));
                    }
                }
            }
        });

        layout.addView(solve, 3, 0);
        setContentView(layout);
    }


}
