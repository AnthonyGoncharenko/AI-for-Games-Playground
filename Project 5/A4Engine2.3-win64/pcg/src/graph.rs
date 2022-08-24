use crate::edge::Edge;
use derive_getters::Getters;
use priority_queue::PriorityQueue;

#[derive(Getters, Debug)]
pub struct Graph {
    width: i32,
    height: i32,
    num_vertices: i32,
    edges: Vec<Edge>,
}

impl Graph {
    pub fn new(width: i32, height: i32) -> Graph {
        Graph {
            width,
            height,
            num_vertices: width * height,
            edges: Vec::new(),
        }
    }

    fn populate_edges(&mut self) -> () {
        self.edges = Vec::new();

        for i in 0..self.width {
            for j in 0..self.height {
                let curr = self.width * i + j;

                if i != self.width - 1 {
                    let down = (i + 1) * self.width + j;
                    self.edges.push(Edge::new(curr, down));

                    if j != self.height - 1 {
                        let right = i * self.width + j + 1;
                        self.edges.push(Edge::new(curr, right));
                    }
                }
            }
        }
    }

    pub fn kruskal(&mut self) -> PriorityQueue<&Edge, &i32> {
        self.populate_edges();
        let mut p_edges: PriorityQueue<&Edge, &i32> = PriorityQueue::new();

        for edge in self.edges() {
            p_edges.push(edge, edge.weight());
        }

        let mut mst = PriorityQueue::new();
        let mut parents: Vec<i32> = (0..self.num_vertices).collect();

        loop {
            if p_edges.is_empty() || mst.len() >= self.num_vertices as usize - 1 {
                break;
            }
            if let Some(e) = p_edges.pop() {
                let set_1: i32 = Self::find(&parents, e.0.start());
                let set_2: i32 = Self::find(&parents, e.0.end());

                if set_1 != set_2 {
                    mst.push(e.0, e.1);
                    Self::union(&mut parents, set_1, set_2);
                }
            }
        }
        mst
    }

    fn find(parent: &Vec<i32>, vertex: &i32) -> i32 {
        if parent[*vertex as usize] != *vertex {
            Self::find(parent, &parent[*vertex as usize])
        } else {
            *vertex
        }
    }

    fn union(parent: &mut Vec<i32>, set_1: i32, set_2: i32) -> () {
        let idx = Self::find(&parent, &set_2) as usize;
        let jdx = Self::find(&parent, &set_1);
        parent[idx] = jdx
    }
}
