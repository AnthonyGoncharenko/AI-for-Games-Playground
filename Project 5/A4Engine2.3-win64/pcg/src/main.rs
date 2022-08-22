mod edge;
mod graph;
mod objects;

fn main() {
    let edge = edge::Edge::new(5, 19);
    println!("{}", edge.repr());
}
