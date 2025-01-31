var nodes = new vis.DataSet([
{id: 1, label: "Hotel Sol", title: "Adyacencias: Hotel Luna"},
{id: 2, label: "Hotel Luna", title: "Adyacencias: Hotel Sol, Hotel Estrella"},
{id: 3, label: "Hotel Estrella", title: "Adyacencias: Hotel Luna, Hotel Mar"},
{id: 4, label: "Hotel Mar", title: "Adyacencias: Hotel Estrella, Hotel Montaña"},
{id: 5, label: "Hotel Montaña", title: "Adyacencias: Hotel Mar, Hotel Valle"},
{id: 6, label: "Hotel Valle", title: "Adyacencias: Hotel Montaña, Hotel Nube"},
{id: 7, label: "Hotel Nube", title: "Adyacencias: Hotel Valle, Hotel Bosque"},
{id: 8, label: "Hotel Bosque", title: "Adyacencias: Hotel Nube, Hotel Fuego"},
{id: 9, label: "Hotel Fuego", title: "Adyacencias: Hotel Bosque, Hotel Brisa"},
{id: 10, label: "Hotel Brisa", title: "Adyacencias: Hotel Fuego"},
]);
var edges = new vis.DataSet([
{from: 1, to: 2},
{from: 2, to: 1},
{from: 2, to: 3},
{from: 3, to: 2},
{from: 3, to: 4},
{from: 4, to: 3},
{from: 4, to: 5},
{from: 5, to: 4},
{from: 5, to: 6},
{from: 6, to: 5},
{from: 6, to: 7},
{from: 7, to: 6},
{from: 7, to: 8},
{from: 8, to: 7},
{from: 8, to: 9},
{from: 9, to: 8},
{from: 9, to: 10},
{from: 10, to: 9},
]);
var container = document.getElementById("mynetwork");
var data = {
  nodes: nodes,
  edges: edges,
};
var options = {
  interaction: {hover: true}
};
var network = new vis.Network(container, data, options);
