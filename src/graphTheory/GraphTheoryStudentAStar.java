package graphTheory;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;

public class GraphTheoryStudentAStar extends JFrame implements GraphAlgorithmListener {
    
    JSplitPane pane = new JSplitPane();
    JPanel leftPanel = new JPanel(new GridLayout(6,1));
    JPanel rightPanel = new JPanel();
    JButton newButton = new JButton("New...");
    JButton addVertexButton = new JButton("Add Vertex...");
    JButton addEdgeButton = new JButton("Add Edge...");
    JButton showGraphButton = new JButton("Show Text Version of Graph...");
    JButton saveGraphButton = new JButton("Save Graph...");
    JButton loadGraphButton = new JButton("Load Graph...");
    JMenuBar menuBar = new JMenuBar();
    JMenu algorithmsMenu = new JMenu("Algorithms");
    JPopupMenu visualizePopup = new JPopupMenu();
    JMenuItem depthFirstItem = new JMenuItem("Depth First Search");
    JMenuItem breadthFirstItem = new JMenuItem("Breadth First Search");
    JMenuItem dijkstraItem = new JMenuItem("Dijkstra\'s Algorithm");
    JMenuItem aStarZeroItem = new JMenuItem("A* - Zero Heuristic");
    JMenuItem aStarDistanceItem = new JMenuItem("A* - Distance Heuristic");
    JMenuItem startItem = new JMenuItem("Set as Start");
    JMenuItem goalItem = new JMenuItem("Set as Goal");
    JMenuItem removeGoalItem = new JMenuItem("Remove from Goals");
    
    ArrayList<PositionedVertex> vertexLocations = new ArrayList<PositionedVertex>();
    ArrayList<MarkedEdge> markedEdges = new ArrayList<MarkedEdge>();
    PositionedVertex start = null;
    GraphAlgorithms algorithms = new GraphAlgorithms();
    
    boolean isWeighted = false;
    boolean isPositioned = false;
    boolean calculateWeights = false;
    
    Graph graph = new Graph("New Graph");
    
    PositionedVertex lastSelected = null;
    
    public static void main(String[] args) {
        new GraphTheoryStudentAStar();
    }
    
    public PositionedVertex vertexToPositionedVertex(Vertex v) {
        for (PositionedVertex pv:vertexLocations) {
            if (pv.vertex.name.equals(v.name))
                return pv;
        }
        return null;
    }
    
    public GraphTheoryStudentAStar() {
        
        //Default Window Setup
        super("Graph Creator - New Graph");
        setSize(650,450);
        setLocation(200,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //Initial Settings
        addVertexButton.setEnabled(false);
        addEdgeButton.setEnabled(false);
        showGraphButton.setEnabled(false);
        saveGraphButton.setEnabled(false);
        
        //Add to Frame
        setContentPane(pane);
        pane.setLeftComponent(leftPanel);
        pane.setRightComponent(rightPanel);
        leftPanel.add(newButton);
        leftPanel.add(loadGraphButton);
        leftPanel.add(addVertexButton);
        leftPanel.add(addEdgeButton);
        leftPanel.add(showGraphButton);
        leftPanel.add(saveGraphButton); 
        
        //menus
        setJMenuBar(menuBar);
        menuBar.add(algorithmsMenu);
        algorithmsMenu.add(depthFirstItem);
        algorithmsMenu.add(breadthFirstItem);
        algorithmsMenu.addSeparator();
        algorithmsMenu.add(dijkstraItem);
        algorithmsMenu.addSeparator();
        algorithmsMenu.add(aStarZeroItem);
        algorithmsMenu.add(aStarDistanceItem);
        
        visualizePopup.add(startItem);
        visualizePopup.add(goalItem);
        visualizePopup.add(removeGoalItem);
        
        //Anonymous Listeners
        //New... Button Listener
        newButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int response = JOptionPane.YES_OPTION;
                        if (graph.getSize()>0) {
                            response = JOptionPane.showConfirmDialog(GraphTheoryStudentAStar.this, "Are you sure that you wish to delete this graph\n"+
                                    "and start over?","New Graph",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
                        }
                        if (response != JOptionPane.YES_OPTION)
                            return;
                        String newName = JOptionPane.showInputDialog(GraphTheoryStudentAStar.this,"What is the name of the new Graph?","Name of Graph",
                                JOptionPane.QUESTION_MESSAGE);
                        graph = new Graph(newName);
                        GraphTheoryStudentAStar.this.setTitle("Graph Creator - "+newName);
                        response = JOptionPane.showConfirmDialog(GraphTheoryStudentAStar.this, "Will the new graph be weighted?",
                                "Weighted Graph?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                        isWeighted = (response==JOptionPane.YES_OPTION);
                        if (isWeighted) {
                            response = JOptionPane.showConfirmDialog(GraphTheoryStudentAStar.this,
                                    "Will the graph use actual Cartesian positioning?","Positioned Graph",
                                    JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE);
                            isPositioned = (response==JOptionPane.YES_OPTION);
                        }
                        if (isPositioned) {
                            response = JOptionPane.showConfirmDialog(GraphTheoryStudentAStar.this,
                                    "Should edge weights be automatically calculated?",
                                    "Autocalculate Weights",JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE);
                            calculateWeights = (response==JOptionPane.YES_OPTION);
                        }
                        addVertexButton.setEnabled(true);
                        showGraphButton.setEnabled(false);
                        addEdgeButton.setEnabled(false);
                        saveGraphButton.setEnabled(false);
                        start = null;
                        revisualize(true);
                    }
                });
        //Add Vertex... Button Listener
        addVertexButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String name = JOptionPane.showInputDialog(GraphTheoryStudentAStar.this,"What is the name of the vertex to be added?",
                                "Vertex Name",JOptionPane.QUESTION_MESSAGE);
                        Vertex v = new Vertex(name);
                        if (isPositioned) {
                            int xPos = Integer.parseInt(JOptionPane.showInputDialog(
                                    GraphTheoryStudentAStar.this,"Enter x coordinate for "+name,
                                    "Vertex x Postion",JOptionPane.QUESTION_MESSAGE));
                            int yPos = Integer.parseInt(JOptionPane.showInputDialog(
                                    GraphTheoryStudentAStar.this,"Enter y coordinate for "+name,
                                    "Vertex y Postion",JOptionPane.QUESTION_MESSAGE));
                            v = new LocationVertex(name,xPos,yPos);
                        }
                        if (!graph.contains(v))
                            graph.addVertex(v);
                        showGraphButton.setEnabled(true);
                        if (graph.getSize()>1)
                            addEdgeButton.setEnabled(true);
                        saveGraphButton.setEnabled(true);
                        revisualize(true);
                    }
             
                });
        //Add Edge... Button Listener
        addEdgeButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String name1 = JOptionPane.showInputDialog(GraphTheoryStudentAStar.this,"What is the name of the first vertex for this edge?",
                                "Vertex Name",JOptionPane.QUESTION_MESSAGE);
                        String name2 = JOptionPane.showInputDialog(GraphTheoryStudentAStar.this,"What is the name of the second vertex for this edge?",
                                "Vertex Name",JOptionPane.QUESTION_MESSAGE);
                        if (!isWeighted) {
                            graph.addEdge(new Vertex(name1), new Vertex(name2));
                        }
                        else if (calculateWeights) {
                            LocationVertex lv1 = (LocationVertex)(graph.nameToVertex(name1));
                            LocationVertex lv2 = (LocationVertex)(graph.nameToVertex(name2));
                            double xDistance = Math.pow(lv1.xLocation-lv2.xLocation, 2);
                            double yDistance = Math.pow(lv1.yLocation-lv2.yLocation,2);
                            graph.addEdge(lv1,lv2,(float)Math.sqrt(xDistance+yDistance));
                        }
                        else {
                            float weight = Float.parseFloat(JOptionPane.showInputDialog(GraphTheoryStudentAStar.this,"What is the weight of the edge?",
                                "Vertex Name",JOptionPane.QUESTION_MESSAGE));
                            graph.addEdge(new Vertex(name1), new Vertex(name2),weight);
                        }
                        revisualize(false);
                    }
                });
        //Show Text Version of Graph... Button Listener
        showGraphButton.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                       JOptionPane.showMessageDialog(GraphTheoryStudentAStar.this, graph, "Graph Listing",JOptionPane.PLAIN_MESSAGE);
                   } 
                });
        //Save Graph... Button Listener
        saveGraphButton.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                      try {
                           JFileChooser chooser = new JFileChooser();
                           File namedFile = new File(graph.getName()+".graph");
                           chooser.setSelectedFile(namedFile);
                           int option = chooser.showSaveDialog(GraphTheoryStudentAStar.this);
                           if (option==JFileChooser.APPROVE_OPTION) {
                               graph.reset();
                               new ObjectOutputStream(new FileOutputStream(chooser.getSelectedFile())).writeObject(graph);
                           }
                       }
                       catch(Exception ex) {
                           JOptionPane.showMessageDialog(GraphTheoryStudentAStar.this,"Save Failed","Save Failed",JOptionPane.ERROR_MESSAGE);
                           return;
                       }
                   } 
                });
        //Load Graph... Button Listener
        loadGraphButton.addActionListener(
                new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                       try {
                           JFileChooser chooser = new JFileChooser();
                           int option = chooser.showOpenDialog(GraphTheoryStudentAStar.this);
                           if (option==JFileChooser.APPROVE_OPTION) {
                               graph = (Graph) new ObjectInputStream(new FileInputStream(chooser.getSelectedFile())).readObject();
                           }
                       }
                       catch(Exception ex) {
                           JOptionPane.showMessageDialog(GraphTheoryStudentAStar.this,"Save Failed","Save Failed",JOptionPane.ERROR_MESSAGE);
                           return;
                       }
                       
                       GraphTheoryStudentAStar.this.setTitle("Graph Creator - "+graph.getName());
                       
                       if (graph.getSize()>0) {
                           showGraphButton.setEnabled(true);
                           saveGraphButton.setEnabled(true);
                           
                       }
                       else {
                           showGraphButton.setEnabled(false);
                           saveGraphButton.setEnabled(false);
                           
                       }
                       if (graph.getSize()>1)
                           addEdgeButton.setEnabled(true);
                       else
                           addEdgeButton.setEnabled(false);
                       addVertexButton.setEnabled(true);
                       if (graph.getSize()>1) {
                           Vertex v = graph.getVertices().get(0);
                           if (v instanceof LocationVertex)
                               isPositioned=true;
                           else
                               isPositioned=false;
                       }
                       revisualize(true);
                   } 
                });
        //Depth First Search
        depthFirstItem.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               markedEdges.clear();
               revisualize(false);
               if (start!=null) {
                    String output = algorithms.depthFirstSearch(graph, start.vertex);
                    JOptionPane.showMessageDialog(GraphTheoryStudentAStar.this,output,
                            "Path Found",JOptionPane.INFORMATION_MESSAGE);
               }
               else
                   JOptionPane.showMessageDialog(GraphTheoryStudentAStar.this,
                           "Start must be set.","No start set",JOptionPane.ERROR_MESSAGE);
           } 
        });    
        //Breadth First Search
        breadthFirstItem.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               markedEdges.clear();
               revisualize(false);
               if (start!=null) {
                    String output = algorithms.breadthFirstSearch(graph, start.vertex);
                    JOptionPane.showMessageDialog(GraphTheoryStudentAStar.this,output,
                            "Path Found",JOptionPane.INFORMATION_MESSAGE);
               }
               else
                   JOptionPane.showMessageDialog(GraphTheoryStudentAStar.this,
                           "Start must be set.","No start set",JOptionPane.ERROR_MESSAGE);
           } 
        });
        //Dijsktra
        dijkstraItem.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               markedEdges.clear();
               revisualize(false);
               if (start!=null) {
                    String output = algorithms.dijkstra(graph, start.vertex);
                    JOptionPane.showMessageDialog(GraphTheoryStudentAStar.this,output,
                            "Path Found",JOptionPane.INFORMATION_MESSAGE);
               }
               else
                   JOptionPane.showMessageDialog(GraphTheoryStudentAStar.this,
                           "Start must be set.","No start set",JOptionPane.ERROR_MESSAGE);
           } 
        });
        //A* - Zero Heuristic
        aStarZeroItem.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               markedEdges.clear();
               revisualize(false);
               if (start!=null) {
                   int goals = 0;
                   for(Vertex v:graph.getVertices()) {
                       if (v.isGoal())
                           goals++;
                   }
                   if (goals != 1) {
                       JOptionPane.showMessageDialog(GraphTheoryStudentAStar.this,
                               "This version of A* can only be run on algorithms with exactly one goal node",
                               "Number of Goals Error",JOptionPane.ERROR_MESSAGE);
                       return;
                   }
                    String output = algorithms.aStar(graph, start.vertex,new ZeroHeuristic());
                    JOptionPane.showMessageDialog(GraphTheoryStudentAStar.this,output,
                            "Path Found",JOptionPane.INFORMATION_MESSAGE);
               }
               else
                   JOptionPane.showMessageDialog(GraphTheoryStudentAStar.this,
                           "Start must be set.","No start set",JOptionPane.ERROR_MESSAGE);
           } 
        });
        //A* - Distance Heuristic
        aStarDistanceItem.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               markedEdges.clear();
               revisualize(false);
               if (start!=null) {
                   int goals = 0;
                   for(Vertex v:graph.getVertices()) {
                       if (v.isGoal())
                           goals++;
                   }
                   if (goals != 1) {
                       JOptionPane.showMessageDialog(GraphTheoryStudentAStar.this,
                               "This version of A* can only be run on algorithms with exactly one goal node",
                               "Number of Goals Error",JOptionPane.ERROR_MESSAGE);
                       return;
                   }
                    String output = algorithms.aStar(graph, start.vertex,new DistanceHeuristic());
                    JOptionPane.showMessageDialog(GraphTheoryStudentAStar.this,output,
                            "Path Found",JOptionPane.INFORMATION_MESSAGE);
               }
               else
                   JOptionPane.showMessageDialog(GraphTheoryStudentAStar.this,
                           "Start must be set.","No start set",JOptionPane.ERROR_MESSAGE);
           } 
        });
        //Start select
        startItem.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               if (lastSelected==null)
                   return;
               start = lastSelected;
               revisualize(false);
           } 
        });
        //Goal select
        goalItem.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               if (lastSelected==null) {
                   return;
               }
               lastSelected.vertex.setGoal(true);

               revisualize(false);
           } 
        });
        //Goal select
        removeGoalItem.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               if (lastSelected==null)
                   return;
               lastSelected.vertex.setGoal(false);
               revisualize(false);
           } 
        });
        algorithms.addGraphAlgorithmListener(this);
        setVisible(true);
    }
    
    public Vertex getStart() {
        return start.vertex;
    }
    
    public void nodeVisited(NodeEvent e) {
        if (e.getParent() == null) {
            JOptionPane.showMessageDialog(GraphTheoryStudentAStar.this,"Algorithm Started\n"+
                    e.getVisitedNode().getName()+" Removed.");
            return;
        }
        PositionedVertex v1 = vertexToPositionedVertex(e.getParent());
        PositionedVertex v2 = vertexToPositionedVertex(e.getVisitedNode());
        System.out.println("Adding marked edge between "+v2.vertex.name+ " and "+
                v1.vertex.name+".");
        markedEdges.add(new MarkedEdge(v1,v2));
        revisualize(false);
        JOptionPane.showMessageDialog(GraphTheoryStudentAStar.this, e.visitedNode.name+" removed");
    }
    
    /* Method that redraws the graph visualization when necessary
     * recompute is used to determine when the vertices must be automatically
     * repositioned even after user modification
     */
    protected void revisualize(boolean recompute) {
        
        //if the recompute flag is true, determine the default locations for
        //each of the vertices. If not, keep the user-defined locations
        if (recompute) {
            vertexLocations = new ArrayList<PositionedVertex>();
            markedEdges.clear();
        for (int i=0;i<graph.getSize();i++) {
            //populate the Positioned Vertex list with positioned versions
            //of each vertex
            Vertex v = graph.getVertices().get(i);
            PositionedVertex pv = new PositionedVertex();
            pv.vertex = v;
            //x and y locations are determined by the formula for the location
            //of an n-sided polygon - this places them evenly around a circle
            pv.x = (int)(200*Math.cos(2*Math.PI*i/graph.getSize())+200);
            pv.y = (int)(200*Math.sin(2*Math.PI*i/graph.getSize())+200);

            //if x or y are too close the edges, move them out to create a buffer
            pv.x = Math.max(pv.x, 30);
            pv.x = Math.min(pv.x, 370);
            pv.y = Math.max(pv.y, 30);
            pv.y = Math.min(pv.y, 370);
            vertexLocations.add(pv);
        }

        }
        //whether or not positions were recomputed, recreate the visualization
        rightPanel = new VisualizationPanel(); 
        GraphTheoryStudentAStar.this.pane.setRightComponent(rightPanel);
    }
    
    class VisualizationPanel extends JPanel implements MouseMotionListener, MouseListener {
        
        //offsets to make dragging appear to happen from the center of the circle
        private int dragFromX = 0;
        private int dragFromY = 0;
        //Vertex being dragged
        private PositionedVertex dragging = null;
        
        public VisualizationPanel() {
            addMouseMotionListener(this);
            addMouseListener(this);
            setBackground(Color.WHITE);
        }
        
        @Override
        public void paint(Graphics g) {
           super.paint(g);
           
           for (PositionedVertex v:vertexLocations) {
               g.setColor(Color.BLACK);
               //draw vertices
               if (v.vertex.isGoal()) {
                   g.setColor(Color.GREEN);
               }
                else if (start==v || v.vertex.isVisited()) {
                   g.setColor(Color.RED);
                   g.drawString(new Float(v.vertex.getDistanceTraveled()).toString(), v.x-5, v.y+15);
                }
                else if (v.vertex instanceof LocationVertex &&
                   ((LocationVertex)v.vertex).getTotalCost()>0) {
                    g.setColor(Color.RED);
                    LocationVertex lv = (LocationVertex)v.vertex;
                    float drawCost = ((int)(lv.getTotalCost()*100));
                    drawCost/=100;
                    g.drawString(new Float(drawCost).toString(),v.x-5, v.y+15);
                }
                g.fillOval(v.x-5, v.y-5, 10, 10);

               //draw vertex names
               if (!isPositioned)
                    g.drawString(v.vertex.name,v.x-5 ,v.y-10 );
               else {
                   LocationVertex lv = (LocationVertex) v.vertex;
                   g.drawString(v.vertex.name+" ("+lv.xLocation+","+lv.yLocation+")",
                           v.x-10, v.y-10);
               }
           }
                g.setColor(Color.BLACK);
               for(Vertex ov:graph.getVertices()) {
                   for(Edge e:ov.getEdges()) {
                       //match positioned vertices with their original version
                       //so that we can find their edges
                       PositionedVertex first = new PositionedVertex();
                       PositionedVertex second = new PositionedVertex();
                       for(PositionedVertex v:vertexLocations) {
                           if (v.vertex.name.equals(e.v1.getName()))
                               first = v;
                           if (v.vertex.name.equals(e.v2.getName()))
                               second = v;
                       }
                       //draw the edge
                       g.drawLine(first.x,first.y,second.x,second.y);
                       //attempt to draw weight
                       float drawWeight = ((int)(e.getWeight()*100));
                       drawWeight/=100;
                       //g.drawString(new Float(e.getWeight()).toString(),(first.x+second.x)/2,(first.y+second.y)/2);
                       g.drawString(new Float(drawWeight).toString(),(first.x+second.x)/2,(first.y+second.y)/2);
                   }
               }
               g.setColor(Color.GREEN);
               for(MarkedEdge e:markedEdges) {
                   g.drawLine(e.v1.x,e.v1.y,e.v2.x,e.v2.y);
               }
               g.setColor(Color.BLACK);
        }
        
        public void mouseMoved(MouseEvent e) {
            //do nothing if not dragging
        }
        
        public void mouseDragged(MouseEvent e) {
            //do nothing if not dragging a vertex
            if (dragging!=null) {
                //determine position of dragged vertex
                dragging.x = e.getX() - 5 - dragFromX;
                dragging.y = e.getY() - 5 - dragFromY;
                
                //keep in acceptable range - allow dragging outwards though
                //since window can be resized
                dragging.x = Math.max(dragging.x, 30);
                dragging.y = Math.max(dragging.y, 30);

                //continually erase the previous version of the vertex and draw
                //it in its new location
                this.repaint();
            }
        }
        
        public void mouseEntered(MouseEvent e) {
            //no need to do anything
        }
        
        public void mouseExited(MouseEvent e) {
            //if we leave the visualization, we are no longer dragging
            dragging = null;
        }
        
        public void mouseReleased(MouseEvent e) {
            //do nothing if we were not dragging
            if (!e.isPopupTrigger() && dragging!=null) {
                //determine new location
                dragging.x = e.getX() - 5 - dragFromX;
                dragging.y = e.getY() - 5 - dragFromY;
                
                //keep in acceptable range - allow dragging outwards though
                //since window can be resized
                dragging.x = Math.max(dragging.x, 30);
                dragging.y = Math.max(dragging.y, 30);
                
                
                //instead of repainting, create a new visualization panel to 
                //freeze the vertex in its new location. Note that this
                //effectively resets dragging as well
                GraphTheoryStudentAStar.this.revisualize(false);
            }
            //popup menu
            else if (e.isPopupTrigger()) {
                System.out.println("Right Click");
                //determine click location
                int x = e.getX();
                int y = e.getY();

                GraphTheoryStudentAStar.this.revisualize(false);

                for(PositionedVertex pv:vertexLocations) {
                    if (x>=pv.x-5 && x<=pv.x+5 && y>=pv.y-5 && y<=pv.y+5) {
                        lastSelected = pv;
                        visualizePopup.show(GraphTheoryStudentAStar.this,pv.x+230,pv.y+65);
                        return;
                    }
                }
            }
        }
        
        public void mousePressed(MouseEvent e) {
            //determine click location
            int x = e.getX();
            int y = e.getY();
            
            //if there is a vertex where we clicked, mark it as the dragging
            //vertex and set offsets
            for(PositionedVertex pv:vertexLocations) {
                if (x>=pv.x-5 && x<=pv.x+5 && y>=pv.y-5 && y<=pv.y+5) {
                    dragging = pv;
                    dragFromX = x - pv.x-5;
                    dragFromY = y - pv.y-5;
                    return;
                }
            }
            //clicked where there is no vertex, so not dragging
            dragging = null;
        }
        
        public void mouseClicked(MouseEvent e) {
            
        }
    }
}




