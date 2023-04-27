resource "azurerm_servicebus_namespace" "namespace-esame" {
  name                = "namespace-coda"
  location            = azurerm_resource_group.esame.location
  resource_group_name = azurerm_resource_group.esame.name
  sku                 = "Basic"
}

resource "azurerm_servicebus_queue" "coda-esame" {
  name         = "coda-esame"
  namespace_id = azurerm_servicebus_namespace.namespace-esame.id
}