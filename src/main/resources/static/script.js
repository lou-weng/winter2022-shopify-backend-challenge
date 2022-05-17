const getAllInventoryItems = async () => {
    try {
        const response = await fetch('/inventory-items/getAll', {
            method: 'GET'
        });
        let inventoryItems =  await response.json();
        return inventoryItems
    } catch {
        throw new Error("Something went wrong!")
    }
}

async function deleteItemWithId() {
    try {
        let itemId = document.getElementById("deleteItemId").value
        document.getElementById("deleteItemId").value = ""

        console.log(itemId)
        const response = await fetch(`/inventory-items/delete/${itemId}`, {
            method: 'POST',
        });
        console.log(await response)
    } catch (e) {
        console.log(e)
        throw new Error("Something went wrong!")
    }
    loadAllInventoryItems()
}

async function createItem() {
    try {
        let itemId = document.getElementById("createItemId").value
        document.getElementById("createItemId").value = ""
        let itemName = document.getElementById("createItemName").value
        document.getElementById("createItemName").value = ""
        let itemQuantity = document.getElementById("createItemQuantity").value
        document.getElementById("createItemQuantity").value = ""
        let itemUpdate = document.getElementById("createItemUpdate").value
        document.getElementById("createItemUpdate").value = ""
        
        const response = await fetch(`/inventory-items/create`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                name: itemName,
                quantity: itemQuantity,
                lastUpdated: itemUpdate,
            }),
        });
        console.log(await response)
    } catch (e) {
        console.log(e)
        throw new Error("Something went wrong!")
    }
    loadAllInventoryItems()
}

async function updateItem() {
    try {
        let itemId = document.getElementById("createItemId").value
        document.getElementById("createItemId").value = ""
        let itemName = document.getElementById("createItemName").value
        document.getElementById("createItemName").value = ""
        let itemQuantity = document.getElementById("createItemQuantity").value
        document.getElementById("createItemQuantity").value = ""
        let itemUpdate = document.getElementById("createItemUpdate").value
        document.getElementById("createItemUpdate").value = ""

        const response = await fetch(`/inventory-items/update/${itemId}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                name: itemName,
                quantity: itemQuantity,
                lastUpdated: itemUpdate,
            }),
        });
    } catch (e) {
        console.log(e)
        throw new Error("Something went wrong!")
    }
    loadAllInventoryItems()
}

async function loadAllInventoryItems() {

    let table = document.getElementById("itemTableBody")
    table.innerHTML = ""
    try {
        let result = await getAllInventoryItems();
        for (let i of result) {
            console.log(i)
            let row = table.insertRow(0)
            let c1 = row.insertCell(-1)
            c1.innerHTML = i.itemId
            let c2 = row.insertCell(-1)
            c2.innerHTML = i.name
            let c3 = row.insertCell(-1)
            c3.innerHTML = i.quantity
            let c4 = row.insertCell(-1)
            c4.innerHTML = i.lastUpdated
        }
    } catch (e) {
        table.innerText = "Sorry something failed " + e
    }
}

const getAllShipmentItems = async () => {
    try {
        const response = await fetch('/shipments/getAll', {
            method: 'GET'
        });
        let shipmentItems =  await response.json();
        return shipmentItems
    } catch {
        throw new Error("Something went wrong!")
    }
}

async function loadAllShipments() {

    let table = document.getElementById("shipmentTableBody")
    table.innerHTML = ""
    try {
        let result = await getAllShipmentItems();
        for (let i of result) {
            console.log(i)
            let row = table.insertRow(0)
            let c1 = row.insertCell(-1)
            c1.innerHTML = i.shipmentId
            let c2 = row.insertCell(-1)
            c2.innerHTML = i.shipDate
            let c3 = row.insertCell(-1)
            c3.innerHTML = i.destination
        }
    } catch (e) {
        table.innerText = "Sorry something failed " + e
    }
}

async function deleteShipmentWithId() {
    try {
        let shipmentId = document.getElementById("deleteShipmentId").value
        document.getElementById("deleteShipmentId").value = ""

        const response = await fetch(`/shipments/delete/${shipmentId}`, {
            method: 'POST',
        });
        console.log(await response)
    } catch (e) {
        console.log(e)
        throw new Error("Something went wrong!")
    }
    loadAllShipments()
}

async function createShipment() {
    try {
        let shipmentDate = document.getElementById("createShipmentDate").value
        document.getElementById("createShipmentDate").value = ""
        let shipmentDest = document.getElementById("createDestination").value
        document.getElementById("createDestination").value = ""
        
        const response = await fetch(`/shipments/create`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                shipDate: shipmentDate,
                destination: shipmentDest,
            }),
        });
        console.log(await response)
    } catch (e) {
        console.log(e)
        throw new Error("Something went wrong!")
    }
    loadAllShipments()
}

const getAllShipmentItemsInShipment = async () => {
    try {
        let shipmentId = document.getElementById("getShipmentIdForItems").value
        const response = await fetch(`/inventory-items/get/${shipmentId}`, {
            method: 'GET'
        });
        let inventoryItems =  await response.json();
        return inventoryItems
    } catch {
        throw new Error("Something went wrong!")
    }
}

async function loadAllShipmentItems() {

    let table = document.getElementById("shipmentItemsTableBody")
    table.innerHTML = ""
    try {
        let result = await getAllShipmentItemsInShipment();
        for (let i of result) {
            console.log(i)
            let row = table.insertRow(0)
            let c1 = row.insertCell(-1)
            c1.innerHTML = i.itemId
            let c2 = row.insertCell(-1)
            c2.innerHTML = i.name
            let c3 = row.insertCell(-1)
            c3.innerHTML = i.quantity
        }
    } catch (e) {
        table.innerText = "Sorry something failed " + e
    }
}

async function addItemToShipment() {
    try {
        let shipmentId = document.getElementById("addShipmentId").value
        document.getElementById("addShipmentId").value = ""
        let itemId = document.getElementById("addItemId").value
        document.getElementById("addItemId").value = ""
        let quantity = document.getElementById("addItemQuantity").value
        document.getElementById("addItemQuantity").value = ""
        
        const response = await fetch(`/shipments/addToShipment/${shipmentId}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                itemId: itemId,
                quantity: quantity,
            }),
        });
        console.log(await response)
    } catch (e) {
        console.log(e)
        throw new Error("Something went wrong!")
    }
    loadAllShipmentItems()
}