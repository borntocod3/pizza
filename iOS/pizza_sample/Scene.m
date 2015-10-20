//
//  Scene.m
//  pizza_sample
//
//  Created by YokoyamaDaisuke on 2015/08/11.
//  Copyright (c) 2015年 YokoyamaDaisuke. All rights reserved.
//

#import "Scene.h"

@implementation Scene

- (id)initWithSize:(CGSize)size
{
    self = [super initWithSize:size];
    if (self) {
    }
    return self;
}

# pragma mark - Touch

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event
{
    UITouch *touch = [touches anyObject];
    CGPoint locaiton = [touch locationInNode:self];

    NSString *smokePath = [[NSBundle mainBundle] pathForResource:@"smoke" ofType:@"sks"];
    SKEmitterNode *smoke = [NSKeyedUnarchiver unarchiveObjectWithFile:smokePath];
    smoke.position = locaiton;
    smoke.xScale = smoke.yScale = 0.5f;
    [self addChild:smoke];

    SKAction *scale = [SKAction scaleTo:0.5 duration:0.5];
    SKAction *fadeout = [SKAction fadeOutWithDuration:6.0];
    SKAction *remove = [SKAction removeFromParent];
    SKAction *sequence = [SKAction sequence:@[scale, fadeout, remove]];
    [smoke runAction:sequence];
}

-(void)update:(CFTimeInterval)currentTime
{
    /* Called before each frame is rendered */
}

@end
