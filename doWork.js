
class DBSCANClustering {
  // TODO field declaration are not fully supported
  // inputValues = [];
  // minNumElements = 2;
  // epsilon = 1.0;
  // visitedPoints = new Set(); // should be Set
  // metric = (a, b) => 0;

  constructor(inputValues, minNumElements, epsilon, metric) {
    this.inputValues = inputValues;
    this.minNumElements = minNumElements;
    this.epsilon = epsilon;
    this.metric = metric;
    this.visitedPoints = new Set();
  }

  getNeighbours(jsonValue) {
    let verifyValue2 = JSON.parse(jsonValue);
    let neighbours = [];
    for (let candidate of this.inputValues) {
      if (this.metric(verifyValue2, candidate) <= this.epsilon) {
        neighbours.push(JSON.stringify(candidate));
      }
    }
    return neighbours;
  }

  static mergeRightToLeftCollection(neighbours1, neighbours2) {
    for (let tempPt of neighbours2) {
      if (!neighbours1.includes(tempPt)) {
        neighbours1.push(tempPt);
      }
    }
    return neighbours1;
  }

  performClustering() {
    let resultList = [];
    this.visitedPoints.clear();

    let neighbours = [];
    let index = 0;
    while (this.inputValues.length > index) {
      let p = this.inputValues[index];
      let inputValue = JSON.stringify(p);
      if (!this.visitedPoints.has(inputValue)) {
        this.visitedPoints.add(inputValue);
        let neighbours = this.getNeighbours(inputValue);

        if (neighbours.length >= this.minNumElements) {
          let ind = 0;
          while (neighbours.length > ind) {
            let jsonNeighbour = neighbours[ind];
            // let jsonNeighbour = JSON.stringify(r);
            if (!this.visitedPoints.has(jsonNeighbour)) {
              this.visitedPoints.add(jsonNeighbour);
              let individualNeighbours = this.getNeighbours(jsonNeighbour);
              if (individualNeighbours.length >= this.minNumElements) {
                neighbours = DBSCANClustering.mergeRightToLeftCollection(
                    neighbours, individualNeighbours)
              }
            }
            ind++
          }
          resultList.push(neighbours)
        }
      }
      index++
    }
    for (let i = 0;i < resultList.length;i++) {
      let cluster = resultList[i];
      for (let j = 0;j < cluster.length;j++) {
        cluster[j] = JSON.parse(cluster[j]);
      }
      resultList[i] = cluster;
    }

    return resultList;
  }
}

const euclidean = (v1, v2) => {
  return Math.sqrt(Math.pow(v1[0] - v2[0], 2) + Math.pow(v1[1] - v2[1], 2));
}


self.addEventListener('message', function(e) {
  console.log("start worker", performance.now());
  let postData = e.data;
  let data = postData.imageData;
  let inputValues = [];
  for (let i = 0; i < data.length; i += 4) {
      let avg = (data[i] + data[i +1] + data[i +2]) / 3;
      data[i]     = avg; // red
      data[i + 1] = avg; // green
      data[i + 2] = avg; // blue
  }
  for (let y = 0;y < e.data.height - 1;y++) {
      let y0 = y * e.data.width;
      for (let x = 0;x < e.data.width - 1;x++) {
          let xx = (y0 + x) * 4;
          let xxx = (y0 + e.data.width + x) * 4;
          data[xx] = parseInt(Math.abs(
              data[xx + 4] - data[xx] + data[xxx] - data[xx] + data[xxx + 4] - data[xx]
          ));
          if (data[xx] > 0.0) {
              inputValues.push([x, y]);
          }
          data[xx + 1] = data[xx];
          data[xx + 2] = data[xx + 1];
          data[xx + 3] = 255;
      }
  }
  // console.log(inputValues);

  // ctx2.putImageData(imageData, 0, 0);

  let dbscan = new DBSCANClustering(inputValues, 2, 1, euclidean);
  let clusters = dbscan.performClustering();
  // console.log(clusterss);
  let foundPoints = [];
  for (let cluster of clusters) {
      let minX = Number.MAX_VALUE;
      let minY = Number.MAX_VALUE;
      let maxX = Number.MIN_VALUE;
      let maxY = Number.MIN_VALUE;
      for (let [ax, ay] of cluster) {
          if (minX > ax) {
              minX = ax;
          }
          if (minY > ay) {
              minY = ay;
          }
          if (maxX < ax) {
              maxX = ax;
          }
          if (maxY < ay) {
              maxY = ay;
          }
      }
      // console.log(minX, minY, maxX, maxY);
      foundPoints.push([minX, minY, maxX, maxY]);
      //
      // ctx2.beginPath();
      // ctx2.strokeStyle = "red";
      // ctx2.rect(minX, minY, maxX - minX, maxY - minY);
      // ctx2.stroke();
  }

  console.log("finish worker", performance.now());

  self.postMessage(foundPoints);
}, false);