resource "azurerm_container_registry" "acr" {
  name                = "containerRegistryesamecloud"
  resource_group_name = azurerm_resource_group.esame.name
  location            = azurerm_resource_group.esame.location
  sku                 = "Basic"
  admin_enabled       = false
}