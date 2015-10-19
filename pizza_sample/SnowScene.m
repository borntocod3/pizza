//
//  SnowScene.m
//  pizza_sample
//
//  Created by YokoyamaDaisuke on 2015/08/13.
//  Copyright (c) 2015年 YokoyamaDaisuke. All rights reserved.
//

#import "SnowScene.h"

@interface SnowScene()

@property (nonatomic, strong) SKSpriteNode *present;

@end

@implementation SnowScene

-(id)initWithSize:(CGSize)size
{
    if (self = [super initWithSize:size]) {
        
        CGRect physicsFrame = CGRectMake(0, 20, self.frame.size.width, self.frame.size.height - 20);
        self.physicsBody = [SKPhysicsBody bodyWithEdgeLoopFromRect:physicsFrame];
        self.physicsWorld.gravity = CGVectorMake(0.0f, -1.0f);
        
        self.backgroundColor = [SKColor blackColor];

        SKSpriteNode *back = [SKSpriteNode spriteNodeWithImageNamed:@"snow_bg"];
        back.name = @"back";
        back.size = size;
        back.position = CGPointMake(CGRectGetMidX(self.frame),
                                    CGRectGetMidY(self.frame));
        [self addChild:back];
        
        SKSpriteNode *fog = [SKSpriteNode spriteNodeWithImageNamed:@"fog2"];
        fog.name = @"fog";
        fog.size = size;
        fog.color = [SKColor whiteColor];
        fog.colorBlendFactor = 1;
        fog.position = CGPointMake(CGRectGetMidX(self.frame),
                                   CGRectGetMidY(self.frame));
        [self addChild:fog];
        
        NSString *path = [[NSBundle mainBundle] pathForResource:@"snow" ofType:@"sks"];
        SKEmitterNode *snow = [NSKeyedUnarchiver unarchiveObjectWithFile:path];
        snow.position = CGPointMake(CGRectGetMidX(self.frame), CGRectGetMaxY(self.frame));
        [self addChild:snow];
    }
    
    return self;
}

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event
{
    for (UITouch *touch in touches) {
        if (_present == nil) {
            _present = [[SKSpriteNode alloc] initWithImageNamed:@"giftbox.png"];
            _present.name = @"gift";
            _present.size = CGSizeMake(80, 80);
            SKPhysicsBody *pbody = [SKPhysicsBody bodyWithRectangleOfSize:CGSizeMake(80, 80)];
            pbody.restitution = 0.3;
            _present.physicsBody = pbody;
            _present.position = CGPointMake(CGRectGetMidX(self.frame), CGRectGetMaxY(self.frame) - 80);
            
            [self addChild:_present];
        } else {
            CGPoint locaiton = [touch locationInNode:self];
            SKNode *nodeAtPoint = [self nodeAtPoint:locaiton];
            if ([nodeAtPoint.name isEqualToString:@"gift"]) {
                
                SKSpriteNode *cover = [[SKSpriteNode alloc] initWithColor:[UIColor grayColor] size:self.frame.size];
                cover.position = CGPointMake(CGRectGetMidX(self.frame), CGRectGetMidY(self.frame));
                cover.alpha = 0.8f;
                [self addChild:cover];
                
                SKSpriteNode *coupon = [[SKSpriteNode alloc] initWithImageNamed:@"coupon.png"];
                coupon.name = @"coupon";
                coupon.size = CGSizeMake(300, 300);
                coupon.position = CGPointMake(CGRectGetMidX(self.frame), CGRectGetMidY(self.frame));
                coupon.xScale = coupon.yScale = 0.1f;
                [self addChild:coupon];
                
                SKAction *scale = [SKAction scaleTo:1 duration:2.5];
                SKAction *fadein = [SKAction fadeInWithDuration:2];
                SKAction *sequence = [SKAction sequence:@[scale, fadein]];
                [coupon runAction:sequence];
            }
        }
    }
}

@end
