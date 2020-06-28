export const convertListToTree = (target, source, parentID, action?) => {
  source.filter(u =>
    (parentID === null && u.parent === null) ||
    (u.parent !== null && u.parent.id === parentID)
  ).forEach(u => {
    // let v = u;
    if (!!action) {
      action(u);
    }
    target.push(u);
    u.children = [];
    convertListToTree(u.children, source, u.id, action);
  });
};
export const treeForEach = (nodes, action) => {
  for (let i = 0; i < nodes.length; i++) {
    if (action) {
      action(nodes[i]);
    }
    treeForEach(nodes[i].children, action);
  }
};
