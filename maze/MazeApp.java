package maze;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

public class MazeApp extends JFrame{
  private JLabel statusbar;
  private Maze maze;
  private Agenda<Square> agenda;
  private MazeSolver solver;
  private boolean solving = false;

  public MazeApp(){
    initUI();
  }

  private void initUI(){
    createMenuBar();
    statusbar = new JLabel("status");
    add(statusbar, BorderLayout.SOUTH);
    maze = new Maze();
    add(maze, BorderLayout.CENTER);

    setTitle("Maze App");
    setSize(600, 400);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  private void createMenuBar() {
    JMenuBar menubar = new JMenuBar();
    JMenu file = new JMenu("File");
    JMenuItem load = new JMenuItem("Load Maze");
    load.setToolTipText("Load a new maze configuration");
    load.addActionListener(new OpenFileAction());
    JMenuItem solve = new JMenuItem("Solve Maze");
    solve.setToolTipText("Solve the current maze");
    solve.addActionListener(new SolveMazeAction());

    file.add(load);
    file.add(solve);
    menubar.add(file);

    JMenu agendas = new JMenu("Agenda");
    ButtonGroup group = new ButtonGroup();

    JRadioButtonMenuItem astar = new JRadioButtonMenuItem("A*");
    astar.setSelected(true);
    agendas.add(astar);
    agenda = new AStar<Square>();

    astar.addItemListener(new ItemListener(){
      public void itemStateChanged (ItemEvent e){
        if (!solving && e.getStateChange() == ItemEvent.SELECTED){
          agenda = new AStar<Square>();
          statusbar.setText("use A* agenda");
        }
      }
    });

    JRadioButtonMenuItem stack = new JRadioButtonMenuItem("Stack");
    stack.setSelected(true);
    agendas.add(stack);

    stack.addItemListener(new ItemListener(){
      public void itemStateChanged (ItemEvent e){
        if (!solving && e.getStateChange() == ItemEvent.SELECTED){
          agenda = new MyStack<Square>();
          statusbar.setText("use stack agenda");
        }
      }
    });

    JRadioButtonMenuItem queue = new JRadioButtonMenuItem("Queue");
    queue.setSelected(true);
    agendas.add(queue);

    queue.addItemListener(new ItemListener(){
      public void itemStateChanged (ItemEvent e){
        if (!solving && e.getStateChange() == ItemEvent.SELECTED){
          agenda = new MyQueue<Square>();
          statusbar.setText("use queue agenda");
        }
      }
    });

    group.add(astar);
    group.add(stack);
    group.add(queue);
    menubar.add(agendas);

    setJMenuBar(menubar);
  }

  private void start(){
    new Thread(new Runnable() {
      public void run() {
        System.err.println("start solver");
        solving = true;
        solver = new MazeSolver(maze, agenda);
        boolean success = solver.solve();
        if(success == true){
          System.out.println("This maze is solved:");
          System.out.println(maze);
          statusbar.setText("solved");
        }else{
          System.out.println("This maze is not solvable:");
          System.out.println(maze);
          statusbar.setText("not solvable");
        }
        solving = false;
      }
    }).start();
  }

  private class OpenFileAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
      if (solving){
        return;
      }

      JFileChooser chooser = new JFileChooser();
      //File workingDirectory = new File(System.getProperty("user.dir"));
      chooser.setCurrentDirectory(new File("."));
      //chooser.setCurrentDirectory(workingDirectory);
      int ret = chooser.showDialog(maze, "Load maze file");
      if (ret == JFileChooser.APPROVE_OPTION) {
        String fileName = chooser.getSelectedFile().getName();
        System.err.println("file name: "+fileName);
        // replace old maze with new maze
        try {
          maze.loadFile(new File(fileName));
          maze.repaint();
        } catch (FileNotFoundException e1) {
          JOptionPane.showMessageDialog(maze, "Could not open file!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    }
  }

  private class SolveMazeAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
      if (solving){
        return;
      }
      start();
    }
  }

  public static void main(String[] args) {
    MazeApp app = new MazeApp();
    app.setVisible(true);
  }
}
