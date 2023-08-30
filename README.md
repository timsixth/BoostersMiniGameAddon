# BoostersMiniGameAddon

This plugin is a addon to every minigame which uses T-MiniGameAPI.<br>
Thanks to this addon you can add coins boosters to your minigame.

## How to install?

You can install by install command or download plugin from releases section. 

`
/minigameapi addon install timsixth/BoostersMiniGameAddon latest
`

## Commands

Admin commands
- {minigame_admin_command} booster create - Creates new booster
- {minigame_admin_command} booster list - List of all boosters
- {minigame_admin_command} booster give <global|player_name> <booster_name> - Gives booster to everyone or someone
- {minigame_admin_command} booster help - All booster commands

Player commands:
- /{minigame_player_command} booster help - All booster commands
- /{minigame_player_command} booster list - List of your boosters

## Configuration

config.yml
```yaml
giveBoosterNameMenuTitle: '&aType booster name'
giveBoosterDisplayNameMenuTitle: '&aType booster displayName'
giveBoosterDurationMenuTitle: '&aType booster duration'
giveBoosterMultiplierMenuTitle: '&aType booster multiplier'

messages:
  invalidBoosterMultiplier: '&cCan not create booster, multiplier is invalid'
  createdBooster: '&aYou have created booster'
  invalidBoosterDuration: '&cDuration is invalid, e.g. 12h'
  playerOffline: '&cThis player is offline'
  boosterDoesNotExist: '&cThis booster does not exist'
  givenBooster: '&aYou have given a booster to {PLAYER_NAME}'
  userDoesNotExist: '&cThis user does not exist'
  givenBoosterEveryone: '&aYou have given a booster to everyone'
  boosters: '&aBoosters'
  haveThisBooster: '&cThis user has this booster'
  boosterExpired: '&aYour booster with name {BOOSTER_DISPLAY_NAME} &ahas been expired'
  boosterAlreadyExists: '&cThis booster already exists'
  playerHelp:
    - '&7/&a{player_command} booster help &7- All booster commands'
    - '&7/&a{player_command} booster list &7- List of your boosters'
  adminHelp:
    - '&7/&a{admin_command} booster create &7- Opens GUI to creation boosters'
    - '&7/&a{admin_command} booster help &7- All booster commands'
    - '&7/&a{admin_command} booster give <global|player_name> <booster_name> &7- Gives booster to everyone or someone'
    - '&7/&a{admin_command} booster list &7- List of all boosters'
```
guis.yml
```yaml
guis:
  boosterTypeMenu:
    size: 9
    displayname: '&aType booster type'
    empty_slots:
      material: 'BLACK_STAINED_GLASS_PANE'
    slots:
      3:
        material: 'GREEN_DYE'
        displayname: '&aTemporary'
        click_action:
          type: 'CHOOSE_BOOSTER_TYPE'
          args:
            - 'TEMPORARY'
      5:
        material: 'RED_DYE'
        displayname: '&aPermanently'
        click_action:
          type: 'CHOOSE_BOOSTER_TYPE'
          args:
            - 'PERMANENTLY'
  yourBoosters:
    size: 54
    displayname: '&aYour boosters'
    empty_slots:
      material: 'BLACK_STAINED_GLASS_PANE'
    slots:
      22:
        material: 'RED_WOOL'
        displayname: '&cList is empty'
        click_action:
          type: ''
          args:
            - ''
```
