var sceneWidth = 1024;
var sceneHeight = 800;
var rectSize = 32;
var gridWidth = sceneWidth / rectSize;
var gridHeight = sceneHeight / rectSize;

var squares = []
var fields = []

var paper = Raphael("scene", sceneWidth, sceneHeight);

for (var i = 0; i < gridWidth; ++i) {
  for (var j = 0; j < gridHeight; ++j) {
    var x = i * rectSize;
    var y = j * rectSize;
    var r = paper.rect(x, y, rectSize, rectSize);
    r.node.setAttribute("class", "square");
    squares.push(r);
  }
}

for (var i = 0; i < gridWidth * gridHeight; ++i) {
  fields.push(Math.floor(Math.random() * 4));
}

var classes = {
  0: "",
  1: "c1",
  2: "c2",
  3: "c3"
}

var updateScene = function() {
  for (var i = 0; i < gridWidth * gridHeight; ++i) {
    if (classes[fields[i]] === undefined) {
      squares[i].node.setAttribute("class", "square")
    }
    else {
      squares[i].node.setAttribute("class", "square " + classes[fields[i]])
    }
  }
}

updateScene();

var playerId;
var players = {}

var url = "ws://" + window.location.host;
var ws = new WebSocket(url);

ws.onopen = function()
{
  ws.send(JSON.stringify({ ready: 1 }));
};

ws.onerror = function(e)
{
  ws.send(JSON.stringify({ ready: 0 }));
}

ws.onmessage = function(m)
{
  var jm = JSON.parse(m.data);
  if (jm.fields != null) {
    fields = jm.fields;
    updateScene();
  }
  if (jm.playerId != null) {
    playerId = jm.playerId;
  }
  if (jm.players != null) {
    var updatedPlayers = {};
    for (p of jm.players) {
      var x = rectSize * p.x + 1;
      var y = rectSize * p.y + 1;
      var cp = players[p.id];
      if (cp != null) {
        delete players[p.id];
        updatedPlayers[p.id] = cp;
        cp.attr({"x": x, "y": y});
      }
      else {
        updatedPlayers[p.id] = paper.rect(x, y, rectSize - 1, rectSize - 1);
        updatedPlayers[p.id].attr({fill: "orange"});
      }
    }
    updatedPlayers[playerId].attr({fill: "white"});
    for (var i in players) {
      players[i].remove();
    }
    players = updatedPlayers;
  }
}

ws.onclose = function()
{
  alert("Connection is closed...");
};
