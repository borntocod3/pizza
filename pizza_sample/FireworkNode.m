//
//  FireworkNode.m
//  Fireworks
//
//  Created by John Watson on 4/27/14.
//  Copyright (c) 2014 John Watson. All rights reserved.
//

#import "FireworkNode.h"
#import "SKEmitterNode+EmitterFomResourceNamed.h"

SKColor* RandomColor(void)
{
    static NSArray *colors = 0;
    static int numColors = 0;
    
    if(!colors) {
        colors = @[[SKColor greenColor], [SKColor yellowColor], [SKColor orangeColor], [SKColor cyanColor], [SKColor magentaColor], [SKColor purpleColor]];
        numColors = (int)[colors count];
    }
    
    int index = arc4random_uniform(numColors);
    return colors[index];
}

NSString* RandomNodename(void)
{
    static NSArray *pizzas = 0;
    static int numPizzas = 0;
    
    if(!pizzas) {
        pizzas = @[@"pizza_a", @"pizza_b", @"pizza_c"];
        numPizzas = (int)[pizzas count];
    ;
    }
    
    int index = arc4random_uniform(numPizzas);
    return pizzas[index];
}

@interface FireworkNode ()
@property CGSize size;
@property SKEmitterNode *ascentEmitter;
@property CGMutablePathRef ascentPath;
@property SKColor *color;
@end

@implementation FireworkNode

- (instancetype)initWithSceneBounds:(CGSize)size
{
    static int signCounter = 0;
    self = [super init];
    if(self) {
        self.size = size;
        self.position = CGPointMake(arc4random_uniform(size.width - 100) + 50, 0);
        self.color = RandomColor();
        
        _ascentEmitter = [SKEmitterNode emitterFromResourceNamed:@"Ascent"];
        _ascentEmitter.particleColorSequence = nil;
        _ascentEmitter.particleColorBlendFactor = 1.0;
        _ascentEmitter.particleColor = self.color;
        
        _ascentPath = CGPathCreateMutable();
        CGPathMoveToPoint(_ascentPath, NULL, 0, 100);
        CGFloat offset = signCounter++ % 2 == 0 ? 50 : -50;
        CGPathAddQuadCurveToPoint(_ascentPath, NULL, 0, size.height * 0.5, offset, size.height * 0.75);
        
        [self addChild:_ascentEmitter];
        
        SKAction *sequence = [SKAction sequence:@[[SKAction followPath:_ascentPath duration:1.5], [SKAction performSelector:@selector(explode) onTarget:self]]];
        [self runAction:sequence];
    }
    return self;
}

- (void)dealloc
{
    CGPathRelease(_ascentPath);
}

- (void)explode
{
    self.zPosition = 1;
    [_ascentEmitter removeFromParent];
    
    SKSpriteNode *fog = (SKSpriteNode*)[[self parent] childNodeWithName:@"fog"];
    fog.color = self.color;
    fog.alpha = 1;
    [fog removeAllActions];
    [fog runAction:[SKAction fadeAlphaTo:0 duration:0.9]];

    SKSpriteNode *pizza = (SKSpriteNode*)[[self parent] childNodeWithName:RandomNodename()];
    pizza.color = self.color;
    pizza.alpha = 1;
    CGFloat radius = 150 + arc4random() % 50;
    pizza.size = CGSizeMake(radius, radius);
    pizza.position = CGPointMake(CGRectGetMidX(self.frame), CGRectGetMaxY(self.frame));
    [pizza removeAllActions];
    [pizza runAction:[SKAction fadeAlphaTo:0 duration:0.9]];
    
    SKEmitterNode *explosionEmitter = [SKEmitterNode emitterFromResourceNamed:@"Explosion"];
    explosionEmitter.particleColorSequence = nil;
    explosionEmitter.particleColorBlendFactor = 1.0;
    explosionEmitter.particleColor = self.color;
    [self addChild:explosionEmitter];
    [self runAction:[SKAction sequence:@[[SKAction waitForDuration:0.5], [SKAction removeFromParent]]]];
}

@end
