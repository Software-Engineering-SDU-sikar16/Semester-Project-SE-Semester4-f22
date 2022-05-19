//  <reference path="phaser.d.ts"/>
//      class ObjectEntity {
//         height: number;
//         name: string;
//         properties: any;
//         rectange: boolean;
//         rotation: number;
//         type: string;
//         visible: boolean;
//         width: number;
//         x: number;
//         y: number;
// }
//  class SimpleGame {
//     game: Phaser.Game;
//     map: Phaser.Tilemap;
//     layer: Phaser.TilemapLayer;
//     player: Phaser.Sprite;
//     winZone: Phaser.Rectangle;
//     constructor() {
//         this.game = new Phaser.Game(640, 480, Phaser.AUTO, 'content', {
//             create: this.create, preload:
//             this.preload, update: this.update, render: this.render          });      }
//      preload() {
//         this.game.load.tilemap("ItsTheMap", "newMap.json", null, Phaser.Tilemap.TILED_JSON);
//         var img = this.game.load.image("HF2_A2", "HF2_A2.png");
//         this.game.load.image("Decepticon", "decepticonLarge.png");      }
//      update() {
//         if (this.winZone.contains(this.player.x + this.player.width/2,this.player.y + this.player.height/2))
//             alert("You Win!");        }
//      render() {      }      create() {
//         this.map = this.game.add.tilemap("ItsTheMap", 32, 32, 64, 32);
//         this.map.addTilesetImage("HF2_A2","HF2_A2");
//         this.map.createLayer("Background").resizeWorld();
//         this.player = new Phaser.Sprite(this.game, 0, 0, "Decepticon");
//         this.player.width = 64;
//         this.player.height = 64;
//         this.game.world.addAt(this.player, 1);
//         this.game.camera.follow(this.player);
//         var something = this.map.objects["GameObjects"][0];
//         var start = <ObjectEntity>this.map.objects["GameObjects"][0];
//         var end = <ObjectEntity>this.map.objects["GameObjects"][1];
//         this.winZone = new Phaser.Rectangle(end.x, end.y, end.width, end.height);
//         this.player.position.set(start.x, start.y);
//         this.game.input.keyboard.addKey(Phaser.Keyboard.LEFT).onUp.add(() => {
//             this.player.position.add(-32, 0);          });
//         this.game.input.keyboard.addKey(Phaser.Keyboard.RIGHT).onUp.add(() => {
//             this.player.position.add(32, 0);          });
//         this.game.input.keyboard.addKey(Phaser.Keyboard.UP).onUp.add(() => {
//             this.player.position.add(0,-32);          });
//         this.game.input.keyboard.addKey(Phaser.Keyboard.DOWN).onUp.add(() => {
//             this.player.position.add(0, 32);          });      }  }
//  window.onload = () => {      var game = new SimpleGame();  };
