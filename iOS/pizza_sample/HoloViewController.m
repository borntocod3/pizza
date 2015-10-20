//
//  HoloViewController.m
//  pizza_sample
//
//  Created by YokoyamaDaisuke on 2015/10/12.
//  Copyright © 2015年 YokoyamaDaisuke. All rights reserved.
//

#import "HoloViewController.h"
#import "YLImageView.h"
#import "YLGIFImage.h"

@interface HoloViewController ()

@property (nonatomic, weak) IBOutlet YLImageView *imageView;

@end

@implementation HoloViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    _imageView.image = [YLGIFImage imageNamed:_holoFileName];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
