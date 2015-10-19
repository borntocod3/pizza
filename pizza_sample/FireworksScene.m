//
//  FireworksScene.m
//  pizza_sample
//
//  Created by YokoyamaDaisuke on 2015/08/13.
//  Copyright (c) 2015年 YokoyamaDaisuke. All rights reserved.
//

#import "FireworksScene.h"
#import "FireworkNode.h"

@implementation FireworksScene

- (id)initWithSize:(CGSize)size
{
    if (self = [super initWithSize:size]) {
        
        self.backgroundColor = [SKColor blackColor];

        SKSpriteNode *back = [SKSpriteNode spriteNodeWithImageNamed:@"firework_bg"];
        back.name = @"back";
        back.zPosition = 1;
        back.size = size;
        back.position = CGPointMake(CGRectGetMidX(self.frame),
                                   CGRectGetMidY(self.frame));
        
        [self addChild:back];
        
        SKSpriteNode *fog = [SKSpriteNode spriteNodeWithImageNamed:@"fog"];
        fog.name = @"fog";
        fog.zPosition = 2;
        fog.size = size;
        fog.color = [SKColor clearColor];
        fog.colorBlendFactor = 1;
        fog.position = CGPointMake(CGRectGetMidX(self.frame),
                                   CGRectGetMidY(self.frame));
        
        [self addChild:fog];
        
        SKSpriteNode *pizza_a = [SKSpriteNode spriteNodeWithImageNamed:@"pizza-a"];
        pizza_a.name = @"pizza_a";
        pizza_a.zPosition = 2;
        pizza_a.color = [SKColor clearColor];
        pizza_a.colorBlendFactor = 1;
        
        [self addChild:pizza_a];
        
        SKSpriteNode *pizza_b = [SKSpriteNode spriteNodeWithImageNamed:@"pizza-b"];
        pizza_b.name = @"pizza_b";
        pizza_b.zPosition = 2;
        pizza_b.color = [SKColor clearColor];
        pizza_b.colorBlendFactor = 1;
        
        [self addChild:pizza_b];
        
        SKSpriteNode *pizza_c = [SKSpriteNode spriteNodeWithImageNamed:@"pizza-c"];
        pizza_c.name = @"pizza_c";
        pizza_c.zPosition = 2;
        pizza_c.color = [SKColor clearColor];
        pizza_c.colorBlendFactor = 1;
        
        [self addChild:pizza_c];
    }
    return self;
}

//- (void)addFirework
//{
//    FireworkNode *node = [[FireworkNode alloc] initWithSceneBounds:self.size];
//    node.zPosition = 1;
//    [self addChild:node];
//}

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event
{
    for (UITouch *touch in touches) {
        FireworkNode *firework = [[FireworkNode alloc] initWithSceneBounds:self.size];
        firework.position = CGPointMake([touch locationInNode:self].x, 0);
        firework.zPosition = 3; // change to 1 on explosion
        [self addChild:firework];
    }
}

@end
